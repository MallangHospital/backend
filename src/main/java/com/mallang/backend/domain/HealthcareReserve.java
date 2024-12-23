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
public class HealthcareReserve extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate reserveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // 필드가 null이 아닌 값으로 설정되어야 함
    private HealthcareType hType;

    @Column(nullable = false)
    private String status;

    @PrePersist
    public void prePersist() {
        // hType이 null일 경우 기본값 설정
        if (this.hType == null) {
            this.hType = HealthcareType.개인_건강검진; // 기본값으로 원하는 Enum 값 설정
        }
    }
}