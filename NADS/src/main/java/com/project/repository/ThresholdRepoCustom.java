package com.project.repository;

import java.util.List;

import com.project.Entity.ThresholdEntity;

public interface ThresholdRepoCustom {

	List <ThresholdEntity> findAllByIndexPattern(String indexPattern);
	
	List <ThresholdEntity> findAllByDatetime(String choiceDate, String choiceDateEnd);
}
