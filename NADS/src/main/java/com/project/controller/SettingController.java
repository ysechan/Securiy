package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.Entity.UserEntity;
import com.project.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class SettingController {

    @Autowired
    private UserRepo userRepo;

    // 사용자 정보 수정
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("ip") String ip,
            HttpSession session,
            Model model) {
        
        // 현재 로그인된 사용자 정보 가져오기
        UserEntity currentUser = (UserEntity) session.getAttribute("loginInfo");
        
        if (currentUser != null) {
            currentUser.setName(name);
            currentUser.setMail(email);
            currentUser.setPhone(phone);
            currentUser.setIp(ip);
            
            // 업데이트된 정보 저장
            userRepo.save(currentUser);

            model.addAttribute("message", "정보가 성공적으로 업데이트되었습니다.");
        } else {
            model.addAttribute("error", "로그인 정보가 없습니다.");
        }
        
        return "Home"; // setting.html로 리디렉션
    }
}
