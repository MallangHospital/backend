package com.mallang.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
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

    // 기본 생성자
    public Review() {
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 생성자
    public Review(String content, int star, Doctor doctor, Member member) {
        this.content = content;
        this.star = star;
        this.doctor = doctor;
        this.member = member;
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getWriteDate() {
        return writeDate;
    }

    public int getStar() {
        return star;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Member getMember() {
        return member;
    }
}