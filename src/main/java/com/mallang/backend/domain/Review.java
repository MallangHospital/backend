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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Doctor와의 관계 설정 (다대일)
    @JoinColumn(name = "doctor_id", nullable = false) // 외래키 설정, 반드시 설정
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY) // Department와의 관계 설정 (다대일)
    @JoinColumn(name = "department_id", nullable = false) // 외래키 설정, 반드시 설정
    private Department department;


    @Column(name = "explanation_stars", nullable = false)
    private Integer explanationStars;

    @Column(name = "treatment_result_stars", nullable = false)
    private Integer treatmentResultStars;

    @Column(name = "kindness_stars", nullable = false)
    private Integer staffKindnessStars;

    @Column(name = "cleanliness_stars", nullable = false)
    private Integer cleanlinessStars;

    @Column(name = "average_stars", nullable = false)
    private Double averageStars;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "prove_file_path")
    private String proveFilePath;

    @Column(name = "member_password", nullable = false)
    private String memberPassword;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "doctor_name")
    private String doctorName;
}