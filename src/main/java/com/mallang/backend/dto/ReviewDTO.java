package com.mallang.backend.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long doctorId;
    private Long departmentId;
    private Integer explanationStars;
    private Integer treatmentResultStars;
    private Integer staffKindnessStars;
    private Integer cleanlinessStars;
    private Double averageStars;
    private String content;
    private String proveFilePath; // 첨부 파일 경로 추가
    private String memberPassword;
    private String department;
    private String doctor;
    private String regDate;
}
