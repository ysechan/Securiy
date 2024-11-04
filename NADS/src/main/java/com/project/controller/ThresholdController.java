package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Entity.ThresholdEntity;
import com.project.service.ThresholdService;

@RestController
@RequestMapping("/api/threshold")
public class ThresholdController {
	@Autowired
	private ThresholdService thresholdService;
	
	@GetMapping("/all")
	public ResponseEntity<List<ThresholdEntity>> getAllDocsByPattern(@RequestParam(value = "choiceDate", required = false) String choiceDate,
																	@RequestParam(value = "choiceDateEnd", required = false) String choiceDateEnd) {
        List<ThresholdEntity> thrDoc;
        
        if(choiceDate != null && !choiceDate.isEmpty()) {
        	thrDoc = thresholdService.getDatetime(choiceDate, choiceDateEnd);
        	System.out.println("입력 받은 날짜로 조회한 threshold 데이터 : " + thrDoc);
        }else {
        	thrDoc = thresholdService.getAllDocsByPattern();
        	System.out.println("모든 threshold 데이터 조회 : " + thrDoc);
        }
        return ResponseEntity.ok(thrDoc);
    } 
}
