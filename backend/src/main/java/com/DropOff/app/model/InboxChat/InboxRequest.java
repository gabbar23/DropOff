package com.DropOff.app.model.InboxChat;

/**
 * Chat message request model.
 *
 * 
 */
public class InboxRequest {

    private int senderId;
    private int receiverId;
    private String message;

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
