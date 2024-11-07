package com.project.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataWebSocketHandler extends TextWebSocketHandler {
    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);  // 새로운 WebSocket 세션을 저장
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 클라이언트로부터 메시지를 받았을 때 처리 (필요한 경우 로직 추가)
    }

    public void sendMessage(String data) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(data));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);  // 연결이 끊긴 세션 제거
    }
}
