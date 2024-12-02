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

    private String patientName; // 환자 이름
    private String doctorName; // 의사 이름
    private String registrationDate; // 등록 날짜
    private String registrationTime; // 등록 시간
    private String phoneNumber; // 전화번호
    private String department; // 진료과목
    private String visitType; // 진료유형 (초진, 재진, 상담)
    private String symptom; // 증상
}