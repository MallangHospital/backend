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
    private String name;

    @Column(nullable = false)
    private String specialty; // 전문 분야

    @Column(nullable = false)
    private String contact; // 연락처

    private String photoPath; // 사진 URL

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // 소속 진료과 (양방향 매핑)

}