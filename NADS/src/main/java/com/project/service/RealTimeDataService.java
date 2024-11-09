package com.project.service;

import com.project.config.TrafficDataWebSocketHandler;
import com.project.Entity.ElasticEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealTimeDataService {
	/*
	 * private final ElasticService elasticService; private final
	 * DataWebSocketHandler webSocketHandler;
	 * 
	 * public RealTimeDataService(ElasticService elasticService,
	 * DataWebSocketHandler webSocketHandler) { this.elasticService =
	 * elasticService; this.webSocketHandler = webSocketHandler; }
	 */

    
	/*
	 * @Scheduled(fixedRate = 5000) // 5초마다 실행 public void fetchDataAndSend() {
	 * List<ElasticEntity> data = elasticService.getAllDocsByPattern(); // 데이터 조회
	 * String jsonData = elasticService.convertToJson(data); // JSON 변환 try {
	 * webSocketHandler.sendMessage(jsonData); // WebSocket으로 전송 } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
}
