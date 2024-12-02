package com.mallang.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineRegistrationDTO {

    private Long id; // ID 필드
    private String patientName; // 환자 이름
    private String doctorName; // 의사 이름
    private String phoneNumber; // 전화번호
    private String visitType; // 진료유형 (초진, 재진, 상담)
    private String symptom; // 증상
    private String registrationTime; // 등록 시간
    private String registrationDate;
    private Long departmentId; // 소속 진료과 ID
    private String department; // 소속 진료과 이름
    private Long doctorId; // 의사 ID
}