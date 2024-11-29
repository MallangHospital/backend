package com.mallang.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;                 // 의사 ID
    private String name;             // 의사 이름
    private String specialty;        // 전문 분야
    private String contact;          // 연락처
    private String photoPath;        // 사진 경로 (URL 또는 파일 경로)

    // 휴진 정보 리스트
    private List<VacationDetails> vacations;
}