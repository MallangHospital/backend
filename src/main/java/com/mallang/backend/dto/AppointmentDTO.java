package com.mallang.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long doctorId;             // 의사 ID
    private Long departmentId;         // 진료과 ID
    private String memberId;             // 회원 ID
    private LocalDate appointmentDate; // 예약 날짜
    private LocalTime appointmentTime; // 예약 시간
    private String symptomDescription; // 증상 설명
}