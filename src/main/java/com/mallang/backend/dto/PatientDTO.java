package com.mallang.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id; // 환자 ID
    private String name; // 환자 이름
    private String contact; // 연락처
    private String symptomDescription; // 증상
}