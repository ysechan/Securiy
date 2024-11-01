package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Monthly {
	
	// 기백이형이 html, css 수정하고 편하게 확인해보려고 만든 야매 짬통 겟 맵핑있는 
	// 컨트롤러.. 기백이형이 수정 다하면 지우거나 수정하거나 하시면 됩니다.

    @GetMapping("/monthGph")
    public String showMonth() {
    	System.out.println("김기백화이팅");
        return "monthGph";  // monthGph.html을 반환
    }
    

    @GetMapping("/weekGph")
    public String showWeek() {
    	return "weekGph";
    }
    
    @GetMapping("/countryGph")
    public String showCountry() {
    	return "countryGph";
    }

}


