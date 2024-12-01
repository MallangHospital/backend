package com.mallang.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String proveFilePath;
    private String memberPassword;
    private String department;
    private String doctor;
    private String regDate;

    @JsonInclude(JsonInclude.Include.NON_NULL) // null인 경우에만 JSON에 포함하지 않음
    private Long memberId; // 반환할 때만 포함되도록 설정
}