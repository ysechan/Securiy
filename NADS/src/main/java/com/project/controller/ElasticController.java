package com.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Entity.ElasticEntity;
import com.project.service.ElasticService;

@RestController
@RequestMapping("/api/elastic")
public class ElasticController {
	
	@Autowired
	private ElasticService elasticService;
	
	// 모든 데이터를 반환하는 API
	@GetMapping("/all")
	public ResponseEntity<List<ElasticEntity>> getAllDocsByPattern(){
		List<ElasticEntity> doc = elasticService.getAllDocsByPattern();
		System.out.println("가져 온 데이터 : "+doc);
		return ResponseEntity.ok(doc);
	}
	
}
