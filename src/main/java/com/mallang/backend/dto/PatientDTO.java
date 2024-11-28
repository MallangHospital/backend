package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatientDTO {
    private Long id; // 환자 ID
    private String name; // 환자 이름
    private String contact; // 연락처
    private String symptomDescription; // 증상
}