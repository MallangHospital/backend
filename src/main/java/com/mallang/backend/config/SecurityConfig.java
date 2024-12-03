package com.mallang.backend.config;

import com.mallang.backend.config.jwt.JWTFilter;
import com.mallang.backend.config.jwt.JWTUtil;
import com.mallang.backend.config.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CorsConfigurationSource corsConfigurationSource;

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화 -> stateless 상태, 공격 강화 필요 없음
        http.csrf(csrf -> csrf.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());
      
        // 접근 권한 설정
        /*http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/member/join", "/", "/error", "/login").permitAll() // 인증 없이 접근 가능

                //관리자 허용
                .requestMatchers(HttpMethod.GET, "/api/feedback/**").hasRole("ADMIN") // 피드백 전체 조회, ID조회 허용

                .requestMatchers(HttpMethod.GET, "/api/news/**").hasRole("ADMIN") // 뉴스 전체 조회,특정 ID조회 허용
                .requestMatchers(HttpMethod.POST, "/api/news").hasRole("ADMIN")  // 뉴스 작성 허용
                .requestMatchers(HttpMethod.DELETE, "/api/news/**").hasRole("ADMIN")  // 뉴스 삭제허용
                .requestMatchers(HttpMethod.PUT, "/api/news/**").hasRole("ADMIN")  //뉴스 수정허용


                .requestMatchers(HttpMethod.GET, "/api/doctor").hasRole("ADMIN") // 의사 전체 조회, ID조회는 허용
                .requestMatchers(HttpMethod.POST, "/api/doctor").hasRole("ADMIN")  // 의사 등록 허용
                .requestMatchers(HttpMethod.DELETE, "/api/doctor/**").hasRole("ADMIN")  // 공지 삭제허용
                .requestMatchers(HttpMethod.PUT, "/api/doctor/**").hasRole("ADMIN")  // 공지 수정허용



                .requestMatchers(HttpMethod.GET, "/api/notice").hasRole("ADMIN") // 공지사항 전체 조회, ID조회는 허용
                .requestMatchers(HttpMethod.POST, "/api/notice").hasRole("ADMIN")  // 공지 작성 허용
                .requestMatchers(HttpMethod.DELETE, "/api/notice/**").hasRole("ADMIN")  // 공지 삭제허용
                .requestMatchers(HttpMethod.PUT, "/api/notice/**").hasRole("ADMIN")  // 공지 수정허용*/
                
        http.cors(cors -> cors.configurationSource(corsConfigurationSource)); // CORS 설정 등록

        /*http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/member/join", "/", "/error", "/login").permitAll() // 인증 없이 접근 가능
                .requestMatchers("/api/doctors").permitAll()  // 모든 사용자가 접근 가능
                .requestMatchers("/api/admin").hasRole("ADMIN") // 관리자만 접근 가능
                .anyRequest().authenticated() // 다른 요청은 로그인한 사용자만 접근 가능
        );*/
      
        http.authorizeHttpRequests((auth) -> auth
                .anyRequest().permitAll() // 모든 요청에 대해 인증 없이 접근 가능
        );

        // JWT 필터 등록
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")  // 로그아웃 엔드포인트 (기본값: /logout)
                .logoutSuccessUrl("/")  // 로그아웃 성공 시 리다이렉트할 URL ("/")
                .invalidateHttpSession(true)  // 세션 무효화 (STATELESS 환경이지만 안전을 위해)
                .deleteCookies("JSESSIONID")  // 세션 쿠키 삭제
                .permitAll());

        return http.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CORS 설정 활성화
        http.cors(Customizer.withDefaults());

        // 기본 CSRF 설정 활성화
        http.csrf(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}