package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String>{

	// 로그인에 필요한 기능 : db에서 id, pw 찾기
	public UserEntity findByIdAndPw(String id, String pw);
	
	
	public UserEntity findMailById(String id);

	
}
