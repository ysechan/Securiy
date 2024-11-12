package com.project.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;

@Service
public class SessionThreshService {

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private final ElasticsearchClient sessionThreshClient;
	
	@Autowired
	public SessionThreshService(ElasticsearchClient sessionThreshClient) {
		this.sessionThreshClient = sessionThreshClient;
	}
	
	public SearchResponse<?> searchDocuments(String startDate, String endDate) throws IOException{
		Query sessionThrQuery;
		if(startDate != null && endDate != null) {
			// 시작 및 종료 시간을 KST로 변환
    		LocalDateTime startDateTime = LocalDateTime.parse(startDate); // startDate는 "yyyy-MM-ddTHH:mm:ss" 형식이어야 합니다.
    		ZonedDateTime startDateKST = startDateTime.atZone(ZoneOffset.ofHours(9)); // UTC+9로 변환

    		LocalDateTime endDateTime = LocalDateTime.parse(endDate);
    		ZonedDateTime endDateKST = endDateTime.atZone(ZoneOffset.ofHours(9));

    		sessionThrQuery = Query.of(q -> q
        				.range(r -> r
        				.field("time")
        				.gte(JsonData.of(startDateKST.toString()))	// 시작시간
						.lte(JsonData.of(endDateKST.toString()))
    				)
    		);
    	}else {
    		sessionThrQuery = Query.of(q -> q
        				.range(r -> r
        				.field("time")
        				.gte(JsonData.of("now-31m"))	// 시작시간
        				.lte(JsonData.of("now"))
    				)
    		);
    	}
		
		SearchRequest searchRequestSessionThr = SearchRequest.of(s -> s
    			.index("session_thrashold")
    			.size(0)
    			.query(sessionThrQuery)
    			.aggregations("per_minute", a -> a
    					.dateHistogram(DateHistogramAggregation.of(h -> h
    							.field("time")
    							.calendarInterval(CalendarInterval.Minute)
						))
    					.aggregations("total_sessionThresh", agg -> agg
    							.sum(SumAggregation.of(sum -> sum.field("session")))
						)
				)
		);
    	
    	return sessionThreshClient.search(searchRequestSessionThr, Object.class);
	}
	
}
