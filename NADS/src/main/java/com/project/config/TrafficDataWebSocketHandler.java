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
import com.project.service.SessionService;
import com.project.service.SessionThreshService;
import com.project.service.ThresholdService;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class TrafficDataWebSocketHandler extends TextWebSocketHandler {
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
		try {
			JsonNode dateRange = objectMapper.readTree(message.getPayload());
	        String startDate = dateRange.has("startDate") ? dateRange.get("startDate").asText() : null;
	        String endDate = dateRange.has("endDate") ? dateRange.get("endDate").asText() : null;
	        
	        // 조회 후 WebSocket 세션에 응답 전송
	        fetchDataAndSend(startDate, endDate);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void sendMessage(String dataTraffic, String dataThresh) {
		for (WebSocketSession session : sessions) {
			try {
				if (session.isOpen()) {
					session.sendMessage(new TextMessage(dataTraffic + "\n" + dataThresh));
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
	
	@Scheduled(fixedRate = 5000) // 5초마다 실행
	public void fetchDataAndSendScheduled() {
	    // 기본 조회 시 startDate와 endDate를 null로 전달
	    fetchDataAndSend(null, null);
	}
	
	// Traffic 과 그 Threshold값 가져오기
	public void fetchDataAndSend(String startDate, String endDate) {
		try {
			// ElasticService 에서 데이터 조회
			var searchResponseTraffic = elasticService.searchDocuments(startDate, endDate);
			var searchResponseThresh = thresholdService.searchDocuments(startDate, endDate);
			
			// Aggregation 데이터 추출
			var aggregationsTraffic = searchResponseTraffic.aggregations();
			var aggregationsThresh = searchResponseThresh.aggregations();
			
			// 필요한 데이터만 추출하기 위한 리스트 생성
	        List<Map<String, Object>> dataListTraffic = new ArrayList<>();
	        List<Map<String, Object>> dataListThresh = new ArrayList<>();

	        // "per_minute"라는 이름의 aggregation이 있는지 확인하고 처리
	        var perMinuteAggregation = aggregationsTraffic.get("per_minute");
	        if (perMinuteAggregation != null) {
	            var buckets = perMinuteAggregation.dateHistogram().buckets().array();
	            
	            for (var bucket : buckets) {
	                Map<String, Object> dataTraffic = new HashMap<>();
	                dataTraffic.put("key_as_string", bucket.keyAsString());
	                
	                // "sum#total_txRate" 값을 가져옴
	                var totalTxRateAgg = bucket.aggregations().get("average_txRate");
	                if (totalTxRateAgg != null) {
	                	dataTraffic.put("value", totalTxRateAgg.avg().value());
	                } else {
	                	dataTraffic.put("value", null);
	                }
	                dataListTraffic.add(dataTraffic);
	            }
	        }
	        
	        var perMinuteAggregationThresh = aggregationsThresh.get("per_minute");
	        if (perMinuteAggregationThresh != null) {
	        	var buckets = perMinuteAggregationThresh.dateHistogram().buckets().array();
	        	
	        	for (var bucket : buckets) {
	        		Map<String, Object> dataThresh = new HashMap<>();
	        		dataThresh.put("key_as_string", bucket.keyAsString());
	        		
	        		// "sum#total_txRate" 값을 가져옴
	        		var totalThreshAgg = bucket.aggregations().get("total_threshold");
	        		if (totalThreshAgg != null) {
	        			dataThresh.put("value", totalThreshAgg.sum().value());
	        		} else {
	        			dataThresh.put("value", null);
	        		}
	        		dataListThresh.add(dataThresh);
	        	}
	        }
			// 조회 결과를 JSON으로 변환하여 WebSocket으로 전송
			String jsonDataTraffic = objectMapper.writeValueAsString(dataListTraffic);
			String jsonDataThresh = objectMapper.writeValueAsString(dataListThresh);
			
			// 잘 가져와졌나?
			// System.out.println("Traffic 쿼리문 조회 결과 : " + jsonDataTraffic);
			// System.out.println("Threshold 쿼리문 조회 결과 : " + jsonDataThresh);
			
			sendMessage(jsonDataTraffic, jsonDataThresh);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
