package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacationDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long doctorId;
    private String doctorName; // 추가된 필드
}