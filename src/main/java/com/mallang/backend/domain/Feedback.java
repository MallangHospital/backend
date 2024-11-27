package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자만 사용
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String title; // 건의사항 제목

    @Column(nullable = false, length = 2000)
    private String content; // 건의사항 내용

    @Column(nullable = false)
    private String name; // 고객 이름

    @Column(nullable = false)
    private String phoneNumber; // 고객 휴대폰 번호

    @Column
    private String email; // 고객 이메일 주소 (선택)

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성 시간

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}