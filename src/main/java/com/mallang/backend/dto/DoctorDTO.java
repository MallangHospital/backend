package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialty;
    private String contact;
    private String photoPath; // 사진 경로
}