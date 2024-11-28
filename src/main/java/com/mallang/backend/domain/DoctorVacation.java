package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorVacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor; // Doctor와의 연관 관계

    @Column(nullable = false)
    private LocalDate startDate; // 휴진 시작일

    @Column(nullable = false)
    private LocalDate endDate; // 휴진 종료일
}