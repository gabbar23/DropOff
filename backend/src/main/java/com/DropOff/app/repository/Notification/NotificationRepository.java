package com.DropOff.app.repository.Notification;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Notification.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    /**
     * findByUserOrderByCreatedAt is a method to get notifications by user order by created at
     *
     * 
     * @param user user object
     * @return List<Notification>
     */
    List<Notification> findByUserOrderByCreatedAt(@Param("user") UserProfile user);

    /**
     * deleteNotificationsByUser is a method to delete notifications by user
     *
     * 
     * @param user user object
     */
    void deleteNotificationsByUser(UserProfile user);
}
