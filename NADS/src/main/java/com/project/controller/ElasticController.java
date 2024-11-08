package com.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.project.Entity.ElasticEntity;
import com.project.service.ElasticService;

//@RestController
//@RequestMapping("/api/elastic")
public class ElasticController {
	
	/*
	 * @Autowired private ElasticService elasticService;
	 */
	
	// 모든 데이터를 반환하는 API
	/*
	 * @GetMapping("/all") public ResponseEntity<List<ElasticEntity>>
	 * getAllDocsByPattern(@RequestParam(value = "choiceDate", required = false)
	 * String choiceDate,
	 * 
	 * @RequestParam(value = "choiceDateEnd", required = false) String
	 * choiceDateEnd){ List<ElasticEntity> doc;
	 * 
	 * if(choiceDate != null && !choiceDate.isEmpty()) { doc = elasticService.
	 * getDatetime(choiceDate, choiceDateEnd);
	 * System.out.println("입력 받은 날짜로 조회한 데이터 : " + doc); }else { doc =
	 * elasticService.getAllDocsByPattern(); System.out.println("모든 데이터 조회 : " +
	 * doc); }
	 * 
	 * return ResponseEntity.ok(doc); }
	 */
	
}
