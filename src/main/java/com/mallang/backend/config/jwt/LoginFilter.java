package com.mallang.backend.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.config.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 요청에서 username과 password 읽기
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> loginData = mapper.readValue(request.getInputStream(), Map.class);

            String username = loginData.get("username");
            String password = loginData.get("password");

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Invalid login request format", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 인증된 사용자 정보 가져오기
        CustomMemberDetails customUserDetails = (CustomMemberDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        // 사용자 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // JWT 토큰 생성 (유효 기간: 1시간)
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 1000L);

        // JSON 응답 설정 및 반환
        response.setContentType("application/json"); // 응답 타입 지정
        response.setCharacterEncoding("UTF-8"); // UTF-8 인코딩 설정
        response.getWriter().write("{\"token\":\"" + token + "\"}"); // JSON 형태로 토큰 반환
        response.getWriter().flush(); // 버퍼 플러시
    }

    @Override //로그인 실패 시
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
