package com.project.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "thrashold")
public class ThresholdEntity {

	@Id
	private String id;
	
	@Field(type = FieldType.Text)
	private String time;
	
	@Field(type = FieldType.Long)
	private Long traffic;
	
	@Field(name = "session_count", type = FieldType.Integer)
	private Integer session;
}
 