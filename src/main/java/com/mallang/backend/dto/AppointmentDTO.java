package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class AppointmentDTO {
    private Long id;
    private String patientName; // 환자 이름
    private Long doctorId; // 의사 ID
    private String doctorName; // 의사 이름
    private Long departmentId; // 부서 ID
    private String departmentName; // 부서 이름
    private LocalDate appointmentDate; // 예약 날짜
    private LocalTime appointmentTime; // 예약 시간
    private String symptomDescription; // 증상 설명
    private String status; // 예약 상태 (예: "예약중", "취소")
    private String contact; // 환자 연락처
}