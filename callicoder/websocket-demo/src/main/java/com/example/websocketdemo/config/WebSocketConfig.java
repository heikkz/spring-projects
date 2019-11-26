package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
The @EnableWebSocketMessageBroker is used to enable our WebSocket server.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
    register a websocket endpoint that the clients will use to connect to our websocket server.
    - SockJS is used to enable fallback options for browsers that don’t support websocket.
    - STOMP stands for Simple Text Oriented Messaging Protocol. It is a messaging protocol that defines the format and rules for data exchange.

     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /*
    configuring a message broker that will be used to route messages from one client to another.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // defines that the messages whose destination starts with “/app” should be routed to message-handling methods (we’ll define these methods shortly)
        registry.setApplicationDestinationPrefixes("/app");
        // defines that the messages whose destination starts with “/topic” should be routed to the message broker.
        // Message broker broadcasts messages to all the connected clients who are subscribed to a particular topic.
        registry.enableSimpleBroker("/topic");
    }
}
