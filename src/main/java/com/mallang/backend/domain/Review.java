package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @Column(name = "member_Id", nullable = false)
    private Long memberId; // 리뷰 작성자

    @Column(name = "doctor_Id", nullable = false)
    private Long doctorId; // 리뷰 대상 의사

    @Column(name = "department_Id", nullable = false)
    private Long departmentId; // 리뷰 대상 진료과

    // 추가: 세부 평가 항목별 별점
    @Column(name = "explanation_stars", nullable = false)
    private Integer explanationStars; // 자세한 설명 별점

    @Column(name = "treatment_result_stars", nullable = false)
    private Integer treatmentResultStars; // 치료 후 결과 별점

    @Column(name = "kindness_stars", nullable = false)
    private Integer staffKindnessStars; // 직원의 친절 별점

    @Column(name = "cleanliness_stars", nullable = false)
    private Integer cleanlinessStars; // 청결함 별점

    @Column(name = "average_stars", nullable = false)
    private Double averageStars; // 리뷰 별점 평균 (자동 계산)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 리뷰 본문 내용

    @Column(name = "prove_file_path")
    private String proveFilePath; // 방문 증명 첨부 파일 경로

    @Column(name = "member_password", nullable = false)
    private String memberPassword; // 작성자 비밀번호

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "doctor_name")
    private String doctorName;
}