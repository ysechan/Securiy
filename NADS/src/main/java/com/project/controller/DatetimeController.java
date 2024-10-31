package com.project.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.project.service.ElasticService;

@Controller
public class DatetimeController {
	
	@Autowired
	private ElasticService elasticService;
	
	
}
