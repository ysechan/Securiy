package com.project.service;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Entity.ElasticEntity;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.aggregations.AverageAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;

@Service
public class ElasticService {

	// Web Socket 사용하기 전 elasticsearch 데이터 가져오는 내용
    /*@Autowired
    private ElasticRepo elasticRepo;
    
    public List<ElasticEntity> getAllDocsByPattern() {
        return elasticRepo.findAllByIndexPattern("last_log-*"); 
    }
    
    public List<ElasticEntity> getDatetime(String choiceDate, String choiceDateEnd){
		return elasticRepo.findAllByDatetime(choiceDate, choiceDateEnd);
	}
	*/
	
    // Web Socket 사용할 때 필요한 내용
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /*
    public String convertToJson(List<ElasticEntity> entities) {
        try {
            return objectMapper.writeValueAsString(entities);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }
    */
    
    // Json 쿼리문 사용하여 데이터 조회하기
    // : 조회시간(현재) 10분 전부터 현재까지 1분 단위의 데이터 조회
    private final ElasticsearchClient trafficClient;
    
    @Autowired
    public ElasticService(ElasticsearchClient trafficClient) {
    	this.trafficClient = trafficClient;
    }
    
    public SearchResponse<?> searchDocuments(String startDate, String endDate) throws IOException {
    		Query query;
        	if(startDate != null && endDate != null) {
        		query = Query.of(q -> q
            			.range(r -> r
            				.field("time")
            				.gte(JsonData.of(startDate))	// 시작시간
    						.lte(JsonData.of(endDate))
        				)
        		);
        	}else {
        		query = Query.of(q -> q
            			.range(r -> r
            				.field("time")
            				.gte(JsonData.of("now+9h-30m"))	// 시작시간
        				)
        		);
        	}
        	
        	SearchRequest searchRequest = SearchRequest.of(s -> s
        			.index("last_log-*")
        			.size(0)
        			.query(query)
        			.aggregations("per_minute", a -> a
        					.dateHistogram(DateHistogramAggregation.of(h -> h
        							.field("time")
        							.calendarInterval(CalendarInterval.Minute)
    						))
        					.aggregations("average_txRate", agg -> agg
        							.avg(AverageAggregation.of(avg -> avg.field("tx_rate")))
    						)
    				)
    		);
        	
        	return  trafficClient.search(searchRequest, Object.class);
    }

}
