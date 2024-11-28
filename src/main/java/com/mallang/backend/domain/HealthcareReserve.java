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
public class HealthcareReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hId;

    private String name;

    private String memberId;

    private String phoneNumber;

    private LocalDate reserveDate;

    private String hType; // 단체 건강검진, 개인 건강검진, 기업 건강검진
}