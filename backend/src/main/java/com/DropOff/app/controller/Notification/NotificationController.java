package com.DropOff.app.controller.Notification;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Notification.Notification;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Notification.NotificationRepository;
import com.DropOff.app.service.Authentication.AuthenticationService;
import com.DropOff.app.service.Authentication.AuthenticationServiceImpl;
import com.DropOff.app.utility.DropOff_Sockets.NotificationSocketHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthenticationService authService;

    /**
     * Get all notifications

     * @param json json data
     * @return list of notifications
     */
    @PostMapping
    public ResponseEntity<?> sendNotification(@RequestBody Map<String, String> json) {
        System.out.println("json = " + json);
        try {
            String userId = json.get("userId");
            String title = json.getOrDefault("title", "Notification");
            String message = json.getOrDefault("message", "");
            String payload = json.getOrDefault("payload", "{}");
            String type = json.getOrDefault("type", "default");

            Optional<UserProfile> userOptional = userRepository.findById(Integer.parseInt(userId));
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid sender or receiver ID");
            }

            UserProfile user = userOptional.get();

            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setPayload(payload);
            notification.setType(type);
            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
            System.out.println("notification = " + notification);
            NotificationSocketHandler.getInstance().sendNotification(user.getUser_id(),notification);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
    }

    /**
     * Get all notifications by token

     * @param token token
     * @return list of notifications
     */
    @PostMapping("/get")
    public List<Notification> getNotificationsByToken(@RequestHeader("Authorization") String token) {
        UserProfile user = new AuthenticationServiceImpl().getUserInfo(token);
        if (user == null) {
            throw new RuntimeException("Invalid user ID");
        }

        return new ArrayList<>(notificationRepository.findByUserOrderByCreatedAt(user));
    }

    /**
     * Get all notifications by user id
     * @param userId user id
     * @return list of notifications
     */
    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable int userId) {
        Optional<UserProfile> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid user ID");
        }

        UserProfile user = userOptional.get();

        return new ArrayList<>(notificationRepository.findByUserOrderByCreatedAt(user));
    }

    /**
     * Delete all notifications by user id

     * @param userId user id
     * @return response entity
     */
    @DeleteMapping("/all/{userId}")
    public ResponseEntity<?> deleteNotifications(@PathVariable int userId) {
        Optional<UserProfile> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid user ID");
        }

        UserProfile user = userOptional.get();

        notificationRepository.deleteNotificationsByUser(user);

        return ResponseEntity.ok("Deleted notifications for user with id: " + userId);
    }

}
