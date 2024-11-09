package com.project.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.AverageAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;

@Service
public class SessionService {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private final ElasticsearchClient sessionClient;
	
	@Autowired
	public SessionService(ElasticsearchClient sessionClient) {
		this.sessionClient = sessionClient;
	}
	
	public SearchResponse<?> searchDocuments(String startDate, String endDate) throws IOException {
		Query sessionQuery;
		if(startDate != null && endDate != null) {
			sessionQuery = Query.of(q -> q
        				.range(r -> r
        				.field("time")
        				.gte(JsonData.of(startDate))	// 시작시간
						.lte(JsonData.of(endDate))
    				)
    		);
    	}else {
    		sessionQuery = Query.of(q -> q
        				.range(r -> r
        				.field("time")
        				.gte(JsonData.of("now+9h-30m"))	// 시작시간
    				)
    		);
    	}
		
		SearchRequest sessionSearchRequest = SearchRequest.of(s -> s
				.index("last_log-*")
    			.size(0)
    			.query(sessionQuery)
    			.aggregations("per_minute", a -> a
    					.dateHistogram(DateHistogramAggregation.of(h -> h
    							.field("time")
    							.calendarInterval(CalendarInterval.Minute)
						))
    					.aggregations("average_session", agg -> agg
    							.avg(AverageAggregation.of(avg -> avg.field("session_count")))
						)
				)	
		);
		
		return sessionClient.search(sessionSearchRequest, Object.class);
	}
}
