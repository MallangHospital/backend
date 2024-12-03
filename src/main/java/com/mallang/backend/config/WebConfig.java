package com.mallang.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 외부화된 경로 설정 (application.properties에서 주입받음)
    @Value("${file.upload.path.dev:src/main/resources/static/uploads/}")
    private String devUploadPath;

    @Value("${file.upload.path.prod:/var/www/uploads/}")
    private String prodUploadPath;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    // CORS 설정 (application.properties에서 주입받음)
    @Value("${allowed.origins:http://localhost:5500}")
    private String[] allowedOrigins;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 프로파일에 따라 동적으로 파일 경로 설정
        String uploadPath = "prod".equals(activeProfile) ? prodUploadPath : devUploadPath;
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath) // 프로파일에 따라 경로 선택
                .setCachePeriod(3600); // 캐시 시간 설정 (초 단위)
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로 허용
                .allowedOrigins(allowedOrigins) // 허용할 도메인 (외부화된 설정)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키 및 인증정보 허용
                .maxAge(3600); // CORS 응답 캐시 시간 (초 단위)
    }
}