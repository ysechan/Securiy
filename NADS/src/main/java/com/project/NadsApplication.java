package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class NadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadsApplication.class, args);
	}
	
	// 이 거지같은
	@GetMapping("/")
	public String Home() {
		return "Home";
	}

}
