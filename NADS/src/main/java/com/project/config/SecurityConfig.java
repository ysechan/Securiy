package com.project.config; // 혹은 com.project.security

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // 모든 요청에 대해 인증 비활성화
            )
            .csrf(csrf -> csrf.disable());  // 필요시 CSRF 비활성화

        return http.build();
    }
}
