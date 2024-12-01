package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    // 리뷰 등록

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestPart("reviewDTO") String reviewDTOString,
            @RequestPart(value = "proveFile", required = false) MultipartFile proveFile) {
        try {
            // JSON -> DTO 변환
            ObjectMapper objectMapper = new ObjectMapper();
            ReviewDTO reviewDTO = objectMapper.readValue(reviewDTOString, ReviewDTO.class);

            // 검증 실행
            String validationMessage = validateReview(reviewDTO, proveFile);
            if (validationMessage != null) {
                // 검증 실패 시 경고 메시지 반환
                return ResponseEntity.badRequest().body(validationMessage);
            }

            // 검증 통과 후 리뷰 생성
            ReviewDTO createdReview = reviewService.createReview(reviewDTO, proveFile);
            return ResponseEntity.ok(createdReview);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        }
    }
    // 검증 메서드
    private String validateReview(ReviewDTO reviewDTO, MultipartFile proveFile) {
        if (reviewDTO.getDepartmentId() == null) {
            return "진료과를 선택해주세요.";
        }
        if (reviewDTO.getDoctorId() == null) {
            return "의사를 선택해주세요.";
        }
        if (reviewDTO.getExplanationStars() == null) {
            return "설명 항목에 별점을 선택해주세요.";
        }
        if (reviewDTO.getTreatmentResultStars() == null) {
            return "치료 결과 항목에 별점을 선택해주세요.";
        }
        if (reviewDTO.getStaffKindnessStars() == null) {
            return "친절 항목에 별점을 선택해주세요.";
        }
        if (reviewDTO.getCleanlinessStars() == null) {
            return "청결 항목에 별점을 선택해주세요.";
        }
        if (reviewDTO.getContent() == null || reviewDTO.getContent().trim().isEmpty()) {
            return "리뷰 내용을 입력해주세요.";
        }
        if (proveFile == null || proveFile.isEmpty()) {
            return "병원 방문을 인증할 자료를 업로드해주세요.";
        }
        return null; // 유효성 검사 통과
    }



    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long id,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin) {
        boolean isDeleted = reviewService.deleteReview(id, password, isAdmin);
        if (isDeleted) {
            return ResponseEntity.ok("리뷰가 삭제되었습니다.");
        }
        return ResponseEntity.badRequest().body("비밀번호가 올바르지 않거나 삭제 권한이 없습니다.");
    }

    // 리뷰 조회 (페이지네이션 및 평균 데이터 포함)
    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestParam(value = "page", defaultValue = "1") int page) {
        final int fixedSize = 10;
        Map<String, Object> response = reviewService.getReviewsWithPaginationAndAverages(page, fixedSize);
        return ResponseEntity.ok(response);
    }
}