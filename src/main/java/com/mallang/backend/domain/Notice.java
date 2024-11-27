package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴 추가
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long으로 수정

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String writer; // 작성자

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private Boolean isSecret; // 비밀글 여부

    private String imagePath; // 대표 이미지 경로

    private String attachmentPath; // 첨부 파일 경로

    @Column(nullable = false)
    private String content; // 본문 내용

    private String link; // 관련 링크

    @Column(nullable = false)
    private String status; // 공개 상태 ("공고" 또는 "비공개")

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 작성 시간 기본값 설정

    // 작성 시간 및 기본 상태 설정
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now(); // 작성 시간 자동 설정
        }
        if (status == null) {
            status = Boolean.TRUE.equals(isSecret) ? "비공개" : "공고"; // 비밀 여부에 따라 상태 설정
        }
    }
}