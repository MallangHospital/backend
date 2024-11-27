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
    private String password; // 관리자 비밀번호

    @Column(nullable = false)
    private String content; // 본문 내용


    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 작성 시간 기본값 설정

    // 작성 시간 및 기본 상태 설정
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now(); // 작성 시간 자동 설정
        }
    }
}