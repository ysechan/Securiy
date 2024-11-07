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
        registry.addHandler(dataWebSocketHandler(), "/data")
                .setAllowedOrigins("*"); // 모든 도메인에서 접근 허용
    }

    @Bean
    public DataWebSocketHandler dataWebSocketHandler() {
        return new DataWebSocketHandler();
    }
}
