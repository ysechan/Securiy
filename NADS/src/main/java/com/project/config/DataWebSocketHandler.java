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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataWebSocketHandler extends TextWebSocketHandler {
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

	@Autowired
	private ElasticService elasticService;

	@Autowired
	private ThresholdService thresholdService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session); // 새로운 WebSocket 세션을 저장
	}

	@Scheduled(fixedRate = 1000) // 1초마다 데이터 전송 
	public void sendRealTimeData() {
	// Elastic 데이터 가져오기 
		List<ElasticEntity> elasticData = elasticService.getAllDocsByPattern(); 
		List<ThresholdEntity> thresholdData = thresholdService.getAllDocsByPattern();
	  
		if (!elasticData.isEmpty() && !thresholdData.isEmpty()) { 
			// 마지막 데이터(최신 데이터)가져오기 
			ElasticEntity latestElastic = elasticData.get(elasticData.size() - 1);
			ThresholdEntity latestThreshold = thresholdData.get(thresholdData.size() -1);
	  
			String data = "{ \"time\": \"" + latestElastic.getTime() + "\", " +
		               "\"txRate\": " + latestElastic.getTxRate() + ", " +
		               "\"traffic\": " + (latestThreshold != null ? latestThreshold.getTraffic() : 0) + "}";
			System.out.println("websocket으로 정보 가져오기 : " + data);
	  
			// 모든 WebSocket 세션에 데이터 전송 
			sendMessage(data); 
		} 
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// 클라이언트로부터 메시지를 받았을 때 처리 (필요한 경우 로직 추가)
	}
	
	 private String createJsonData(ElasticEntity elasticEntity, ThresholdEntity thresholdEntity) {
	        return "{ \"time\": \"" + elasticEntity.getTime() + "\", " +
	               "\"txRate\": " + elasticEntity.getTxRate() + ", " +
	               "\"traffic\": " + (thresholdEntity != null ? thresholdEntity.getTraffic() : 0) + "}";
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
}
