package com.DropOff.app.model.InboxChat;

import javax.persistence.*;

import com.DropOff.app.model.Authentication.UserProfile;

import java.time.LocalDateTime;

/**
 * Message model.
 *
 * 
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    private UserProfile receiver;

    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(UserProfile sender) {
        this.sender = sender;
    }

    public void setReceiver(UserProfile receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Integer getSenderId() {
        return sender.getUser_id();
    }

    public Integer getReceiverId() {
        return receiver.getUser_id();
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
