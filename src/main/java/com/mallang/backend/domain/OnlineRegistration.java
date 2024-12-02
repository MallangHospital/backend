    package com.mallang.backend.domain;

    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class OnlineRegistration {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // ID 필드

        @Column(nullable = false)
        private String patientName; // 환자 이름

        @Column(nullable = false)
        private String doctorName; // 의사 이름

        @Column(nullable = false)
        private String phoneNumber; // 전화번호

        @Column(nullable = false)
        private String visitType; // 진료유형 (초진, 재진, 상담)

        @Column(nullable = true)
        private String symptom; // 증상

        @Column(nullable = false)
        private String registrationTime; // 등록 시간

        @Column(nullable = false)
        private String registrationDate;

        @ManyToOne
        @JoinColumn(name = "department_id", nullable = false)
        private Department departmentId; // 소속 진료과 (외래 키)

        @ManyToOne
        @JoinColumn(name = "doctor_id", nullable = false)
        private Doctor doctorId; // 담당 의사 (외래 키)
    }