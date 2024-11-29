package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import jakarta.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id; // 의사 ID

    @NotNull(message = "Department ID cannot be null.")
    private Long departmentId; // 소속 진료과 ID (필수)

    private String name; // 이름
    private String specialty; // 전문 분야 (specialty 추가)
    private String contact; // 연락처
    private String photoUrl; // 사진 URL
    private String position; // 직위
    private String phoneNumber; // 전화번호
    private String photoPath; // 사진 경로
    private String adminId; // 관리자 ID
    private String departmentName; // 소속 진료과 이름
    private List<VacationDTO> vacations; // 휴진 정보 리스트
}