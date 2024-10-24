package com.project.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.Entity.ElasticEntity;
import com.project.repository.ElasticRepo;


// Service : Controller와 Repository 또는 데이터 엑세스 계층 사이의 중간 계층
//		   : Controller에서 DB에 직접적으로 접근하는 것을 막는 역할 등 
@Service
public class ElasticService {

	@Autowired
	private ElasticRepo elasticRepo;
	
	// Repo의 findAll() 불러오기
	public List<ElasticEntity> getAllDoc(){
		
		Page<ElasticEntity> page = elasticRepo.findAll(Pageable.unpaged());
		
		return page.getContent();
	}
	
}
