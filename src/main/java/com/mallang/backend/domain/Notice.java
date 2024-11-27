package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 공지사항 ID

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String noticeWriter; // 작성자 이름

    @Column(nullable = false)
    private String password; // 비밀번호 (작성 시 필요)

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 작성 날짜
}