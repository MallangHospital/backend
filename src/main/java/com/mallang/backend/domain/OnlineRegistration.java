package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자만 유지
public class OnlineRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID 필드 (기본 키)

    private String patientName; // 환자 이름
    private String registrationDate; // 등록 날짜
    private String doctorName; // 의사 이름
    private String symptom; // 증상

    // 필요 시 추가적인 로직은 별도 메서드로 구현
}