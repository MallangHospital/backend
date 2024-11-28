package com.mallang.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id; // 리뷰 ID
    private String memberId; // 작성자 ID
    private Long doctorId; // 의사 ID
    private Long departmentId; // 진료과 ID
    private double star; // 전체 별점
    private List<Integer> detailStars; // 세부 별점 (설명, 치료 결과, 친절도, 청결 등)
    private String content; // 리뷰 내용
    private String memberPassword; // 작성 시 설정한 비밀번호
    private String receiptFilePath; // 병원 방문 인증 파일 경로
    private LocalDateTime createdAt; // 리뷰 작성 날짜
}