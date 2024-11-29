package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientName; // 예약자 이름

    @Column(nullable = false)
    private LocalDateTime registrationDateTime; // 접수 일시

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor; // 담당 의사 (다대일 관계)

    @Column(length = 1000)
    private String details; // 상세 정보
}