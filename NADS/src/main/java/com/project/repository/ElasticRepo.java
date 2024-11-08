//
//package com.project.repository;
//
//import java.util.List;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//import com.project.Entity.ElasticEntity;
//
//@Repository
//public interface ElasticRepo extends ElasticsearchRepository<ElasticEntity, String>, ElasticRepoCustom {
//
//	ElasticEntity findTopByOrderByTimeDesc();
//
//	List<ElasticEntity> findAllByTime(String start, String end);
//	
//	List<ElasticEntity> findAll();
//
//
//	// 내림차순으로 데이터 찾기(?) ElasticEntity findTopByOrderByTimeDesc();
//
//	// 기본적으로 제공되는 findAll() 메서드를 사용하여 모든 데이터 조회 가능
//
//}
