package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String Home() {
		return "landing-page";
	}
	
	@PostMapping("/graphShow")
	public String graphShow(@RequestParam("gph") String gph) {
		if(gph.equals("daily")) {
			return "dayGph";
		}else if(gph.equals("weekly")) {
			return "weekGph";
		}else if(gph.equals("monthly")) {
			return "monthGph";
		}else if(gph.equals("monthly")) {
			return "countryGph"; 
		}else {
			return "cpuGph";
		}
	}
	
	@PostMapping("/elasticTest")
	public String testPage() {
		return "elasticTest";
	}
	
	@GetMapping("/goJoin")
	public String join() {
		return "Join";
	}
	
	@GetMapping("/login")
	public String login() {
		return "Login";
	}
	
}
