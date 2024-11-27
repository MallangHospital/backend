package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DoctorVacationDTO {
    private Long id;
    private String doctorName;
    private LocalDate startDate;
    private LocalDate endDate;
}