package com.project.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.Entity.UserEntity;
import com.project.repository.UserRepo;

@Controller
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	// 회원가입
	@RequestMapping("/completeJoin")
	public String join(@RequestParam("id") String id,
					   @RequestParam("pw") String pw,
					   @RequestParam("name") String name,
					   @RequestParam("email") String email,
					   @RequestParam("phone") String phone,
					   UserEntity userInfo) {
		
		if(id.equals(userInfo.getPw()) || pw.equals(null)) {
			// 아이디가 중복이거나 패스워드가 null인 경우
			return "Join";
		}else {
			userInfo.setId(id);
			userInfo.setPw(pw);
			userInfo.setName(name);
			userInfo.setMail(email);
			userInfo.setPhone(phone);
			
			// 가입일은 현재로 설정
			LocalDateTime now = LocalDateTime.now();
			Timestamp timestamp = Timestamp.valueOf(now);
			userInfo.setJoinDate(timestamp);
			
			userInfo = userRepo.save(userInfo);
			
			return "Home";
		}
	}
	
}
