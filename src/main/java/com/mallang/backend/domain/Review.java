package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 리뷰 ID

    private String content;          // 리뷰 내용
    private LocalDate writeDate;     // 작성 날짜
    private int star;                // 평점

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;           // 리뷰 대상 의사

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;           // 리뷰 작성자

    // @PrePersist를 사용하여 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}