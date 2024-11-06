package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Entity.ElasticEntity;
import com.project.Entity.ThresholdEntity;
import com.project.repository.ElasticRepo;
import com.project.repository.ThresholdRepo;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private ElasticRepo elasticRepository;

    @Autowired
    private ThresholdRepo thresholdRepository;

    @GetMapping("/check-traffic")
    public boolean checkTraffic() {
        // 최신 데이터를 가져온다고 가정하고, 필요한 조회 로직을 추가합니다.
        ElasticEntity latestElastic = elasticRepository.findTopByOrderByTimeDesc();
        ThresholdEntity latestThreshold = thresholdRepository.findTopByOrderByTimeDesc();

        if (latestElastic != null && latestThreshold != null) {
            // txRate가 traffic을 초과할 경우 true 반환
            return latestElastic.getTxRate() > latestThreshold.getTraffic();
        }
        return false;
    }
}
