package com.mallang.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Builder
public class ReviewDTO {

    private Long id; // 리뷰 ID

    @NotNull(message = "진료과 ID는 필수 항목입니다.")
    private Long departmentId; // 진료과 ID

    private String departmentName; // 진료과 이름

    @NotNull(message = "의사 ID는 필수 항목입니다.")
    private Long doctorId; // 의사 ID

    private String doctorName; // 의사 이름

    @NotBlank(message = "리뷰 작성자의 이름은 필수 항목입니다.")
    private String memberName; // 사용자 이름

    @NotNull(message = "리뷰 작성자의 ID는 필수 항목입니다.")
    private Long memberId; // 리뷰 작성자의 ID

    @NotBlank(message = "리뷰 작성자의 비밀번호는 필수 항목입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하여야 합니다.")
    private String memberPassword; // 리뷰 작성자의 비밀번호

    @NotNull(message = "전체 별점은 필수 항목입니다.")
    @Min(value = 0, message = "전체 별점은 최소 0이어야 합니다.")
    @Max(value = 5, message = "전체 별점은 최대 5이어야 합니다.")
    private Double star; // 전체 별점 (정수 또는 소수 지원)

    @NotNull(message = "세분화된 별점은 필수 항목입니다.")
    @Size(min = 4, max = 4, message = "세분화된 별점은 정확히 4개의 값을 가져야 합니다.")
    private List<@Min(0) @Max(5) Integer> detailStar; // 세분화된 별점

    @NotBlank(message = "리뷰 내용은 필수 항목입니다.")
    @Size(max = 1000, message = "리뷰 내용은 최대 1000자까지 입력 가능합니다.")

    private String content; // 리뷰 내용

    private String attachment; // 첨부파일 경로 또는 이름

    private LocalDateTime createdAt; // 리뷰 등록 시간

    // 기본 생성자 추가 (Lombok 사용 시 @NoArgsConstructor도 가능)
    public ReviewDTO() {
    }

    // 명시적 전체 인수 생성자 (필요하면 유지)
    public ReviewDTO(Long id, Long departmentId, String departmentName, Long doctorId, String doctorName, String memberName,
                     Long memberId, String memberPassword, Double star, List<Integer> detailStar, String content,
                     String attachment, LocalDateTime createdAt) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.star = star;
        this.detailStar = detailStar;
        this.content = content;
        this.attachment = attachment;
        this.createdAt = createdAt;
    }
}