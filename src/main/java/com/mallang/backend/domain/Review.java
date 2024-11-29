package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 리뷰 ID

    @Column(name = "member_id", nullable = false)
    private String memberId; // 작성자 ID

    @Column(name = "doctor_name", nullable = false)
    private String doctorName; // 의사 이름

    @Column(name = "department_name", nullable = false)
    private String departmentName; // 진료과 이름

    @ElementCollection
    @CollectionTable(name = "review_detail_stars", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "detail_star")
    private List<Integer> detailStars; // 세부 별점 리스트

    @Column(nullable = false, length = 1000)
    private String content; // 리뷰 내용

    @Column(name = "member_password", nullable = false)
    private String memberPassword; // 비밀번호

    @Column(name = "receipt_file_path")
    private String receiptFilePath; // 병원 방문 인증 파일 경로

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일
}