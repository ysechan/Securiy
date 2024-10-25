package com.project.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="detectify_x")
public class ElasticEntity {

	@Id
	private String id;
	
	@Field(type = FieldType.Integer)
	private Integer mem;
	
	@Field(type = FieldType.Text)
	private String time;
	
	@Field(type = FieldType.Integer)
	private Integer cpu;
	
}
