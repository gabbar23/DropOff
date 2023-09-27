package com.DropOff.app.utility.DropOff_Sockets;


import com.DropOff.app.model.Notification.Notification;
import com.DropOff.app.utility.MyUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

public class NotificationSocketHandler extends TextWebSocketHandler {

    private static NotificationSocketHandler instance;

    /**
     * Singleton instance
     *
     * 
     * @return instance of this class
     */
    public static NotificationSocketHandler getInstance() {
        if (instance == null)
            instance = new NotificationSocketHandler();
        return instance;
    }

    private static final HashMap<String, WebSocketSession> sessions = new HashMap<>();

    /**
     * Send notification to user
     *
     * 
     * @param userId user id
     * @param notification notification
     */
    public void sendNotification(int userId, Notification notification) {
        WebSocketSession receiverSession = sessions.get(String.valueOf(userId));
        try {
            if (receiverSession != null)
                receiverSession.sendMessage(new TextMessage(notification.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle text message from client
     *
     * 
     * @param session current session
     * @param message message from client
     * @throws IOException exception
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        WebSocketSession receiverSession = sessions.get(MyUtils.getUniqueIdForSession(session));
        if (receiverSession != null)
            receiverSession.sendMessage(new TextMessage(payload));
    }

    /**
     * Store session in map
     *
     * 
     * @param session connected session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(MyUtils.getUniqueIdForSession(session), session);
    }

    /**
     * Remove session from map
     *
     * 
     * @param session closed session
     * @param status close status
     * @throws Exception exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession sessionOld = sessions.remove(MyUtils.getUniqueIdForSession(session));
        if (sessionOld != null)
            sessionOld.close();
        super.afterConnectionClosed(session, status);
    }

    /**
     * Get sessions
     *
     * 
     * @return sessions
     */
    public HashMap<String, WebSocketSession> getSessions() {
        return sessions;
    }
}
