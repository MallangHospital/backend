package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String id;                 // 리뷰 ID
    private String memberId;           // 작성자 ID
    private String doctorName;         // 의사 이름
    private String departmentName;     // 진료과 이름
    private List<Integer> detailStars; // 세부 별점 리스트
    private String content;            // 리뷰 본문
    private String memberPassword;     // 작성자 비밀번호
    private String file;               // 첨부 파일 (업로드된 파일 처리)
    private int page;                  // 페이지 번호
    private int size;                  // 페이지 크기
}