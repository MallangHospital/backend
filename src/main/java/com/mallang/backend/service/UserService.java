package com.mallang.backend.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * 관리자 여부를 확인하는 메서드
     *
     * @param token JWT 토큰
     * @return 관리자 여부
     */
    public boolean isAdmin(String token) {
        // JWT 토큰에서 관리자 여부 확인
        if (token != null && token.startsWith("Bearer ")) {
            String actualToken = token.substring(7); // "Bearer " 제거
            // 예시: 관리자 토큰인지 확인하는 로직
            return actualToken.equals("validAdminToken");
        }
        return false;
    }
}