package com.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Entity.UserEntity;
import com.project.repository.UserRepo;

@RestController
public class DDingdongController {

    private final CountApi countApi;
    private final UserRepo userReposi; // UserRepository 주입을 통해 사용자 정보 가져옴

    // 생성자를 통해 CountApi와 UserRepository 주입 (DI - Dependency Injection)
    @Autowired
    public DDingdongController(CountApi countApi, UserRepo userReposi) {
        this.countApi = countApi;
        this.userReposi = userReposi;
    }

    String mail = "";
    
    // 특정 사용자의 이메일 정보를 가져오는 메서드 추가
    @GetMapping("/api/getUserEmail")
    public String getUserEmail(String userId) {
        // userId를 사용하여 UserRepository에서 사용자 정보 조회
        UserEntity user = userReposi.findById(userId).orElse(null);
        if (user != null) {
            String mail = user.getMail();
            return user.getMail(); // 사용자 이메일 정보 반환
        } else {
            return "사용자를 찾을 수 없습니다.";
        }
    }
    
    // '/api/getDdingdongCount' 엔드포인트로 GET 요청을 처리하는 메서드
    @GetMapping("/api/getDdingdongCount")
    public int getCount() {
        // CountApi로부터 현재 count 값을 가져옴
        int count = countApi.getCount();
        // count 값 추적을 위해 출력
        System.out.println("현재 count 값: " + count);
        
        // count가 60의 배수일 때 파이썬 스크립트를 실행
        if (count % 60 == 0 && count != 0) {
            System.out.println("카운트 60 돌파.");
            runPythonScrEmail(mail);
        }
        
        // 현재 count 값을 반환
        return count;
    }
    
    // 파이썬 스크립트를 실행하는 함수
    private void runPythonScrEmail(String mail) {
        try {
            // 실행할 Python 스크립트의 경로 설정
            String pythonScriptPath = "src/main/resources/static/gmailTTTxxx.py";  // Python 스크립트 경로

            // ProcessBuilder를 사용하여 Python 스크립트를 실행하고 인자로 이메일 전달
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python", pythonScriptPath, mail);
            processBuilder.redirectErrorStream(true); // 표준 오류를 표준 출력으로 리다이렉트하여 오류 메시지를 확인할 수 있게 함
            Process process = processBuilder.start();

            // Python 스크립트의 출력 결과를 읽기 위해 BufferedReader 사용
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Python Output: " + line); // Python 스크립트의 출력 결과를 콘솔에 출력
            }

            // 프로세스 종료 코드 확인
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            // 예외 발생 시 스택 트레이스를 출력
            e.printStackTrace();
        }
    }

}
