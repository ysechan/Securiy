package com.project.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KibanaController {

    @GetMapping("/kibana-map")
    public ResponseEntity<Void> getKibanaMap() {
        // Kibana 대시보드 URL
    	String kibanaUrl = "http://223.130.139.47:5601/app/dashboards#/view/f70dd720-9108-11ef-b31e-a3d5599852f9";


        // HTTP 헤더에 리다이렉션 URL 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(kibanaUrl));

        // 리다이렉트 응답 반환
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 리다이렉트
    }
}
