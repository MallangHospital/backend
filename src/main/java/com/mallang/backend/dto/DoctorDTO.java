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

    private Long id;
    private Long departmentId;
    private String name;
    private String specialty;
    private String photoUrl;
    private String position;
    private String phoneNumber;
    private String adminId;
    private String departmentName;
    private List<VacationDTO> vacations; // 휴진 정보 리스트
}