package com.mallang.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorVacationDTO {
    private Long id; // 휴진 ID
    private Long doctorId; // 의사 ID
    private String doctorName; // 의사 이름
    private LocalDate startDate; // 휴진 시작일
    private LocalDate endDate; // 휴진 종료일
}