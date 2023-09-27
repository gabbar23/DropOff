package com.DropOff.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.DropOff.app.utility.DropOff_Sockets.InboxChatSocket;
import com.DropOff.app.utility.DropOff_Sockets.NotificationSocketHandler;

/*
 * Reference: https://spring.io/guides/gs/messaging-stomp-websocket/
 * Reference: https://www.baeldung.com/spring-websockets-send-message-to-user
 * Reference: https://blog.logrocket.com/websocket-tutorial-real-time-node-react/
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * To configure the web socket handlers.
     *
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(InboxChatSocket.getInstance(), "/chatSocket/{userId}")
                .addHandler(NotificationSocketHandler.getInstance(), "/notificationSocket/{userId}")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }

    /**
     * To create a bean of ChatSocketHandler.

     */
    @Bean
    public InboxChatSocket chatHandler() {
        return InboxChatSocket.getInstance();
    }

    /**
     * To create a bean of NotificationSocketHandler.
     */
    @Bean
    public NotificationSocketHandler notificationHandler() {
        return NotificationSocketHandler.getInstance();
    }

}
