package com.project.service;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Entity.ElasticEntity;
import com.project.Entity.ThresholdEntity;
import com.project.repository.ElasticRepo;

@Service
public class ElasticService {

    @Autowired
    private ElasticRepo elasticRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ElasticEntity> getAllDocsByPattern() {
        return elasticRepo.findAllByIndexPattern("last_log-*"); 
    }
    
    public List<ElasticEntity> getDatetime(String choiceDate, String choiceDateEnd){
		return elasticRepo.findAllByDatetime(choiceDate, choiceDateEnd);
	}

    public String convertToJson(List<ElasticEntity> entities) {
        try {
            return objectMapper.writeValueAsString(entities);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }
}
