package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDTO {
    private Long id; // 건강매거진 ID
    private String title; // 제목
    private String newsWriter; // 작성자
    private String password; // 비밀번호 (작성 시 필요)
    private String mainFile; // 대표 이미지 경로
    private String attachment; // 첨부파일 경로
    private String content; // 본문
    private String createdAt; // 작성 날짜 (문자열)
}