//package com.project.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.Entity.ElasticEntity;
//import com.project.Entity.ThresholdEntity;
//import com.project.Entity.UserEntity;
//import com.project.repository.ThresholdRepo;
//import com.project.repository.UserRepo;
//import com.project.service.EmailService;
//import com.project.Entity.ElasticEntity;
//
//@RestController
//@RequestMapping("/api/alerts")
//public class AlertController {
//
////    @Autowired
////    private ElasticRepo elasticRepository;
//
//    @Autowired
//   private ThresholdRepo thresholdRepository;
//
//    @Autowired
//    private UserRepo userRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//   @GetMapping("/check-alerts")
//    public String checkAlerts() {
//        ElasticEntity latestElastic = ElasticEntity.findall();
//        ThresholdEntity latestThreshold = thresholdRepository.findTopByOrderByTimeDesc();
//
//        if (latestElastic != null && latestThreshold != null) {
//            boolean trafficExceeded = latestElastic.getTxRate() > latestThreshold.getTraffic();
//            boolean sessionExceeded = latestElastic.getSessionCount() > latestThreshold.getSession();
//
//            if (trafficExceeded || sessionExceeded) {
//                 // 알림 메시지 생성
//                StringBuilder alertMessage = new StringBuilder("Warning: ");
//                if (trafficExceeded) {
//                    alertMessage.append("TxRate exceeded the threshold. ");
//                }
//                if (sessionExceeded) {
//                    alertMessage.append("Session count exceeded the threshold.");
//                }
//
//                 //현재 로그인한 사용자 가져오기
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                String currentUsername = authentication.getName();
//                UserEntity currentUser = userRepository.findById(currentUsername).orElse(null);
//
//                if (currentUser != null && currentUser.getMail() != null) {
//                    // 현재 로그인한 사용자에게 이메일 발송
//                    emailService.sendEmail(currentUser.getMail(), "Alert Notification", alertMessage.toString());
//                }
//
//                // 알림창 메시지 반환
//                return alertMessage.toString();
//            }
//        }
//        return "정상 상태입니다.";
//    }
//}
