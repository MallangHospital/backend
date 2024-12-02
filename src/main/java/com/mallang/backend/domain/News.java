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
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String newsWriter;

    @Column(nullable = false)
    private String password;

    @Lob
    private String mainFile; // Base64 이미지 데이터

    @Lob
    private String attachment; // Base64 첨부 파일 데이터

    @Column(nullable = false)
    private String content;
}