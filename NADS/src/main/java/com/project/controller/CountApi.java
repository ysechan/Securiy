package com.project.controller; // 패키지 위치 선언 (컨트롤러 패키지에 위치시킬 경우)

import org.springframework.stereotype.Service;

@Service
public class CountApi {

    private int count = 0; // 임계값 초과 횟수를 관리하는 변수

    // count 증가 메서드
    public void incrementCount() {
        count++;
    }

    // 현재 count 값을 반환하는 메서드
    public int getCount() {
        return count;
    }
}
