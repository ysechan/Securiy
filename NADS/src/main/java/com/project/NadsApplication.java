package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableScheduling
public class NadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadsApplication.class, args);
	}
	
	@GetMapping("/")
	public String Home() {
		return "landing-page";
	}

}
