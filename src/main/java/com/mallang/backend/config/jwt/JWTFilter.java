package com.mallang.backend.config.jwt;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.config.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 "Bearer "로 시작하지 않는 경우 다음 필터로 전달
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // "Bearer " 제거 후 토큰만 추출

        try {
            // 토큰 만료 여부 확인
            if (jwtUtil.isExpired(token)) {
                // 로그아웃 요청인지 확인
                if (request.getRequestURI().equals("/logout")) {
                    filterChain.doFilter(request, response); // 로그아웃 요청은 통과
                    return;
                }

                // 만료된 토큰이고 로그아웃이 아닌 경우 401 반환
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                response.getWriter().flush();
                return;
            }

            // 토큰이 유효한 경우 사용자 인증 설정
            authenticateToken(token);

        } catch (Exception e) {
            // 토큰 검증 중 오류 발생 시 401 반환
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token: " + e.getMessage());
            response.getWriter().flush();
            return;
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private void authenticateToken(String token) {
        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token); // 역할 추출

        // Member 객체 생성 및 역할 설정
        Member member = new Member();
        member.setMid(username);

        if ("ROLE_ADMIN".equals(role)) {
            member.changeRole(Role.ROLE_ADMIN); // 관리자 역할 설정
        } else if ("ROLE_MEMBER".equals(role)) {
            member.changeRole(Role.ROLE_MEMBER); // 일반 회원 역할 설정
        }

        // CustomMemberDetails를 사용해 인증 객체 생성
        CustomMemberDetails customUserDetails = new CustomMemberDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities()
        );

        // SecurityContext에 인증 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}