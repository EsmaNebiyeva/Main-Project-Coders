// package org.example.project.security.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry config) {
//         // Mesaj brokerini konfiqurasiya edin
//         config.enableSimpleBroker("/topic"); // "/topic" prefiksi ilə mesajlar göndərilir
//         config.setApplicationDestinationPrefixes("/app"); // İstək göndərmək üçün prefiks
//     }

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         // WebSocket endpoint-i qeyd edin
//         registry.addEndpoint("/ws") // "/ws" endpoint-ini təyin edir
//                 .setAllowedOriginPatterns("*") // CORS dəstəyi üçün bütün domenlərə icazə verir
//                 .withSockJS(); // SockJS dəstəyini aktivləşdirir
//     }
// }

