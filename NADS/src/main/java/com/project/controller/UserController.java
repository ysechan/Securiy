package com.project.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.Entity.UserEntity;
import com.project.repository.UserRepo;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	// 회원가입
	@PostMapping("/completeJoin")
	public String join(@RequestParam("id") String id,
					   @RequestParam("password") String pw,
					   @RequestParam("name") String name,
					   @RequestParam("email") String email,
					   @RequestParam("phone") String phone,
					   UserEntity userInfo) {
		
		boolean idDuplicated = userRepo.existsById(id);
		
		if(idDuplicated || pw == null || pw.trim().isEmpty()) {
			// 아이디가 중복이거나 패스워드가 비어있는 경우
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
			
			// grade는 일단 basic으로 설정
			userInfo.setGrade("basic");
			
			userInfo = userRepo.save(userInfo);
			
			return "Login";
		}
	}
	
	// 아이디 중복확인
	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(UserEntity userInfo,
						  @RequestParam("id") String id) {	// Join.html에 입력된 id값 가져오기
		userInfo = userRepo.findById(id).orElse(null);		// DB에 해당 id값이 있는지 찾아서 userInfo에 담기
		if(userInfo != null) {		// not null이면 DB에 같은 id값이 있다는 뜻
			return "duplicate";		// 중복된 아이디
		}else {
			return "available";
		}
	}
	
	// 로그인
	@PostMapping("/goMain")
	public String login(UserEntity userInfo,
						@RequestParam("id") String id,
						@RequestParam("password") String pw,
						HttpSession loginInfo) {	
		
		// Repository에 만들어 놓은 메소드 가져와서 사용
		userInfo = userRepo.findByIdAndPw(id, pw);
		
		if(userInfo != null) {
			loginInfo.setAttribute("loginInfo", userInfo);
			return "redirect:/main";
		}else {
			return "Login";
		}
	}
	
	// 로그인 유효성 검사
	@GetMapping("/loginConfirm")
	@ResponseBody
	public String loginConfirm(UserEntity userInfo,
							   @RequestParam("id") String id,
							   @RequestParam("password") String pw) {
		
		userInfo = userRepo.findByIdAndPw(id, pw);
		
		if(userInfo != null) {
			return "success";
		}else {
			return "fail";
		}
	}
	
}
