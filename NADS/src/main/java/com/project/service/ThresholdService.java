package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Entity.ThresholdEntity;
import com.project.repository.ThresholdRepo;

@Service
public class ThresholdService {
	
	@Autowired
	private ThresholdRepo thresholdRepo;
	
	// thrashold 인덱스 조회
	public List<ThresholdEntity> getAllDocsByPattern(){
		return thresholdRepo.findAllByIndexPattern("thrashold");
	}
	
	public List<ThresholdEntity> getDatetime(String choiceDate, String choiceDateEnd){
		return thresholdRepo.findAllByDatetime(choiceDate, choiceDateEnd);
	}
}
