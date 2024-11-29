package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long id; // 건강매거진 ID
    private String title; // 제목
    private String newsWriter; // 작성자
    private String password; // 비밀번호 (작성 시 필요)
    private MultipartFile mainFile; // 대표 이미지
    private MultipartFile attachment; // 첨부파일
    private String content; // 본문
    private String regDate;
}