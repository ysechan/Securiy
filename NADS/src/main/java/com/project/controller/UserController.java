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
                       @RequestParam("ipSearch") String ipSearch,
                       UserEntity userInfo) {
        
        boolean idDuplicated = userRepo.existsById(id);
        
        if(idDuplicated || pw == null || pw.trim().isEmpty()) {
            // 아이디가 중복되거나 패스워드가 비어있는 경우
            return "Join";
        } else {
            userInfo.setId(id);
            userInfo.setPw(pw);
            userInfo.setName(name);
            userInfo.setMail(email);
            userInfo.setPhone(phone);
            userInfo.setIp(ipSearch);
            
            // 가입일은 현재로 설정
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            userInfo.setJoinDate(timestamp);
            
            // grade는 일단 basic으로 설정
            userInfo.setGrade("basic");
            
            userRepo.save(userInfo);
            
            return "Login";
        }
    }
    
    // 아이디 중복확인
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("id") String id) { // Join.html에 입력된 id값 가져오기
        UserEntity userInfo = userRepo.findById(id).orElse(null); // DB에 해당 id값이 있는지 찾아서 userInfo에 담기
        if (userInfo != null) { // not null이면 DB에 같은 id값이 있다는 뜻
            return "duplicate"; // 중복된 아이디
        } else {
            return "available";
        }
    }
    
    // 로그인
    @PostMapping("/goMain")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String pw,
                        HttpSession session) {
        
        // Repository에 만들어 놓은 메소드 사용하여 ID와 PW로 사용자 조회
        UserEntity userInfo = userRepo.findByIdAndPw(id, pw);
        
        if (userInfo != null) {
            session.setAttribute("loginInfo", userInfo); // 전체 사용자 정보 세션에 저장
            session.setAttribute("userName", userInfo.getName()); // 사용자 이름만 세션에 별도로 저장
            return "redirect:/main"; // 로그인 성공 시 메인 페이지로 리디렉션
        } else {
            return "Login"; // 로그인 실패 시 로그인 페이지로 이동
        }
    }
    
    // 로그인 유효성 검사
    @GetMapping("/loginConfirm")
    @ResponseBody
    public String loginConfirm(@RequestParam("id") String id,
                               @RequestParam("password") String pw) {
        
        UserEntity userInfo = userRepo.findByIdAndPw(id, pw);
        
        if (userInfo != null) {
            return "success";
        } else {
            return "fail";
        }
    }
}
