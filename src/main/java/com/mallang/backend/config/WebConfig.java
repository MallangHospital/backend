package com.mallang.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 프로파일에 따라 동적으로 파일 경로 설정
        String uploadPath = "prod".equals(activeProfile) ? prodUploadPath : devUploadPath;
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath) // 프로파일에 따라 경로 선택
                .setCachePeriod(3600); // 캐시 시간 설정 (초 단위)
    }
}