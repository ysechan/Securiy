package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(trafficDataWebSocketHandler(), "/traffic")
                .setAllowedOrigins("*"); // 트래픽 엔드포인트
        registry.addHandler(sessionDataWebSocketHandler(), "/session")
                .setAllowedOrigins("*"); // 세션 엔드포인트
    }

    @Bean
    public TrafficDataWebSocketHandler trafficDataWebSocketHandler() {
        return new TrafficDataWebSocketHandler();
    }

    @Bean
    public SessionDataWebSocketHandler sessionDataWebSocketHandler() {
        return new SessionDataWebSocketHandler();
    }
}
