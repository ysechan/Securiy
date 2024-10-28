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
@Document(indexName="last_lsog-20241028")
public class ElasticEntity {

	@Id
	private String id;
	
	@Field(name = "src_ip", type = FieldType.Text)
	private String srcIp;
	
	@Field(name = "session_count", type = FieldType.Integer)
	private Integer sessionCount;
	
	@Field(type = FieldType.Text)
	private String time;
	
	@Field(name = "tx_rate", type = FieldType.Integer)
	private Integer traffic;
	
}
