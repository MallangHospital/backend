package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnlineRegistrationDTO {
    private Long id; // ID 필드
    private String patientName; // 환자 이름
    private String doctorName; // 의사 이름
    private String registrationDate; // 등록 날짜
    private String registrationTime; // 등록 시간
    private String phoneNumber; // 전화번호
    private String department; // 진료과목
    private String visitType; // 진료유형
    private String symptom; // 증상
}