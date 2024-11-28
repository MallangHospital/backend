package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID

    @Column(nullable = false)
    private String memberId; // 응답한 사용자 ID

    @Column(nullable = false)
    private Long questionId; // 질문 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer; // 응답 내용
}