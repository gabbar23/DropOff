package com.DropOff.app.utility;

import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

public class MyUtils {

    /**
     * Get unique id form session
     *
     * 
     * @param session session
     * @return unique id
     */
    public static String getUniqueIdForSession(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
