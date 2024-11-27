package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 피드백 ID

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private String name; // 작성자 이름

    @Column(nullable = false)
    private String phoneNumber; // 작성자 전화번호

    @Column(nullable = false)
    private String email; // 작성자 이메일
}