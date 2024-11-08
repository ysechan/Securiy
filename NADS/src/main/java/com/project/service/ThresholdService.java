package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Entity.ElasticEntity;
import com.project.Entity.ThresholdEntity;
import com.project.repository.ThresholdRepo;

@Service
public class ThresholdService {
	
	@Autowired
	private ThresholdRepo thresholdRepo;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// thrashold 인덱스 조회
	public List<ThresholdEntity> getAllDocsByPattern(){
		return thresholdRepo.findAllByIndexPattern("thrashold");
	}
	
	public List<ThresholdEntity> getDatetime(String choiceDate, String choiceDateEnd){
		return thresholdRepo.findAllByDatetime(choiceDate, choiceDateEnd);
	}
	
	public ThresholdEntity getLatestTrafficByMinute() {
		return thresholdRepo.findTopByOrderByTimeDesc();
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
