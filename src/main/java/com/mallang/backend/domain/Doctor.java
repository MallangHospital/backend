package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 의사 이름

    @Column(nullable = false)
    private String specialty; // 전문 분야 (예: 내과, 산부인과 등)

    @Column(nullable = false)
    private String contact; // 연락처

    @Column(nullable = false)
    private String photoPath; // 사진

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // Department와의 연관 관계
}