package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String newsWriter; // 작성자

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = true)
    private String mainFile; // 대표 이미지

    @Column(nullable = true)
    private String attachment; // 첨부파일 경로

    @Column(nullable = false)
    private String content; // 본문

}