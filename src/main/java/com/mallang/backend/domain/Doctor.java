package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "specialty", nullable = true)
    private String specialty; // 전문 분야

    @Column(name = "photo_url", nullable = true)
    private String photoUrl; // 사진 URL

    @Column(name = "position", nullable = true)
    private String position; // 직위

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacations; // 휴진 정보 리스트 (초기화 없음)

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // 전화번호

    @Column(name = "admin_id", nullable = true)
    private String adminId; // 관리자 ID

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department; // 소속 진료과
}