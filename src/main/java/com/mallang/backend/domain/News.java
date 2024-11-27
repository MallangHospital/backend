package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 건강매거진 ID

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String newsWriter; // 작성자

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = true)
    private String mainFile; // 대표 이미지 경로

    @Column(nullable = true)
    private String attachment; // 첨부파일 경로

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 작성 날짜
}