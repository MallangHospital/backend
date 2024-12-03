package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 건강매거진 ID

    @Column(name = "title", nullable = false)
    private String title; // 제목

    @Column(name = "news_writer", nullable = false)
    private String newsWriter; // 작성자

    @Column(name = "password", nullable = false)
    private String password; // 비밀번호

    @Column(name = "main_file", nullable = true)
    private String mainFile; // 대표 이미지 경로

    @Column(name = "attachment", nullable = true)
    private String attachment; // 첨부파일 경로

    @Column(name = "content", nullable = false)
    private String content; // 본문
}