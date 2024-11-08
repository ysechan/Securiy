package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Entity.ElasticEntity;
import com.project.Entity.ThresholdEntity;
import com.project.service.ElasticService;
import com.project.service.ThresholdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataWebSocketHandler extends TextWebSocketHandler {
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ElasticService elasticService;

	@Autowired
	private ThresholdService thresholdService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session); // 새로운 WebSocket 세션을 저장
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
		sessions.remove(session); // 연결이 끊긴 세션 제거
	}
	
	@Scheduled(fixedRate = 1000) // 1초마다 실행
	public void fetchDataAndSend() {
		try {
			// ElasticService 에서 데이터 조회
			var searchResponse = elasticService.searchDocuments();
			var hits = searchResponse.hits().hits();
			
			// 조회 결과를 JSON으로 변환하여 WebSocket으로 전송
			String jsonData = objectMapper.writeValueAsString(hits);
			
			// 잘 가져와졌나?
			System.out.println("Search Response: " + searchResponse);
			System.out.println("쿼리문 조회 결과 : " + jsonData);
			
			sendMessage(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
