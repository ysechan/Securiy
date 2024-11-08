package com.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {

    private final CountApi countApi;

    // 생성자를 통해 CountApi 주입
    public CountController(CountApi countApi) {
        this.countApi = countApi;
    }

    @GetMapping("/api/getCount")
    public int getCount() {
        return countApi.getCount(); // 현재 count 값을 반환
    }

    // count 값을 증가시키는 엔드포인트 추가
    @PostMapping("/api/incrementCount")
    public void incrementCount() {
        countApi.incrementCount(); // count 값을 증가시킴
    }
}
