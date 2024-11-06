package com.project.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.project.Entity.ThresholdEntity;

@Repository
public interface ThresholdRepo extends ElasticsearchRepository<ThresholdEntity, String>, ThresholdRepoCustom {

	ThresholdEntity findTopByOrderByTimeDesc();

}
