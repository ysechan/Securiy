package com.project.service;

import com.project.Entity.UserEntity;
import com.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    // 로그인 메서드: ID와 PW를 기반으로 사용자 조회
    public UserEntity login(String id, String pw) {
        return userRepo.findByIdAndPw(id, pw);
    }

    // ID 기반으로 사용자 정보 가져오기
    public UserEntity getUserById(String id) {
        return userRepo.findById(id).orElse(null);
    }
}
