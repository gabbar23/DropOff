package com.DropOff.app.controller.InboxChat;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.InboxChat.InboxRequest;
import com.DropOff.app.model.InboxChat.Message;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.InboxChat.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Transactional
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Send message
     * @param request request.
     * @return ResponseEntity.
     */
    @PostMapping
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ResponseEntity<?> sendMessage(@RequestBody InboxRequest request) {
        Optional<UserProfile> senderOptional = userRepository.findById(request.getSenderId());
        Optional<UserProfile> receiverOptional = userRepository.findById(request.getReceiverId());
        if (senderOptional.isEmpty() || receiverOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid sender or receiver ID");
        }

        UserProfile sender = senderOptional.get();
        UserProfile receiver = receiverOptional.get();

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(request.getMessage());
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        return ResponseEntity.ok().build();
    }

    /**
     * Get chat history
     
     * @param senderId   sender id.
     * @param receiverId receiver id.
     * @return List of messages.
     */
    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getChatHistory(@PathVariable int senderId, @PathVariable int receiverId) {
        Optional<UserProfile> senderOptional = userRepository.findById(senderId);
        Optional<UserProfile> receiverOptional = userRepository.findById(receiverId);
        if (senderOptional.isEmpty() || receiverOptional.isEmpty()) {
            throw new RuntimeException("Invalid sender or receiver ID");
        }

        UserProfile sender = senderOptional.get();
        UserProfile receiver = receiverOptional.get();

        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.findBySenderAndReceiverOrderByCreatedAtDesc(sender, receiver));
        messages.addAll(messageRepository.findByReceiverAndSenderOrderByCreatedAtDesc(sender, receiver));
        messages.sort(Comparator.comparing(Message::getCreatedAt));

        return messages;
    }

    /**
     * Get chat users
     *
     * @param userId user id.
     * @return List of users.
     */
    @GetMapping("/{userId}")
    public List<UserProfile> getChatUsers(@PathVariable int userId) {
        Optional<UserProfile> receiverOptional = userRepository.findById(userId);
        if (receiverOptional.isEmpty()) {
            throw new RuntimeException("Invalid user ID");
        }

        UserProfile user = receiverOptional.get();

        List<Integer> userIds =
                messageRepository.findDistinctSenderAndReceiverIdsByUserId(user.getUser_id());

        return userRepository.getUserByIds(userIds);
    }

    /**
     * Remove message
     *
     * @param messageId message id.
     * @return ResponseEntity.
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> removeMessage(@PathVariable long messageId) {
        messageRepository.deleteById(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    /**
     * Remove all messages
     *
     * 
     * @param receiverId receiver id.
     * @param senderId   sender id.
     * @return ResponseEntity.
     */
    @DeleteMapping("/all/{receiverId}/{senderId}")
    public ResponseEntity<?> removeAllMessages(@PathVariable int receiverId, @PathVariable int senderId) {
        Optional<UserProfile> senderOptional = userRepository.findById(senderId);
        Optional<UserProfile> receiverOptional = userRepository.findById(receiverId);
        if (senderOptional.isEmpty() || receiverOptional.isEmpty()) {
            throw new RuntimeException("Invalid sender or receiver ID");
        }

        UserProfile receiver = receiverOptional.get();
        UserProfile sender = senderOptional.get();

        messageRepository.deleteMessagesByReceiverAndSender(receiver, sender);
        messageRepository.deleteMessagesByReceiverAndSender(sender, receiver);
        return ResponseEntity.ok("Messages deleted successfully");
    }




}
