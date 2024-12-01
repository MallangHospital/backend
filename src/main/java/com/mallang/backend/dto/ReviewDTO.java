package com.mallang.backend.dto;

import lombok.*;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;

    @NotNull(message = "의사를 선택해주세요.")
    private Long doctorId;

    @NotNull(message = "진료과를 선택해주세요.")
    private Long departmentId;

    @NotNull(message = "설명 항목에 별점을 선택해주세요.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private Integer explanationStars;

    @NotNull(message = "치료 결과 항목에 별점을 선택해주세요.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private Integer treatmentResultStars;

    @NotNull(message = "친절 항목에 별점을 선택해주세요.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private Integer staffKindnessStars;

    @NotNull(message = "청결 항목에 별점을 선택해주세요.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private Integer cleanlinessStars;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String content;

    private Double averageStars;
    private String proveFilePath;
    private String memberPassword;
    private String department;
    private String doctor;
    private String regDate;
    private String memberId;
}