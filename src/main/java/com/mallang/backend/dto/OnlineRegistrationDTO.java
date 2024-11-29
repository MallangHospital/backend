package com.mallang.backend.dto;

import com.mallang.backend.domain.AppointmentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineRegistrationDTO {

    private Long id; // 등록 ID
    private String patientName; // 예약자 이름
    private LocalDateTime registrationDateTime; // 접수 일시
    private String doctorName; // 담당 의료진 이름
    private String doctorContact; // 담당 의료진 전화번호
    private String departmentName; // 진료 과목
    private String consultationType; // 진료 종류
    private String symptoms; // 증상
    private String details;
}