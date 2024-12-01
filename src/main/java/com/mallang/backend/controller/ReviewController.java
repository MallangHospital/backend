package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.MemberService;
import com.mallang.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final MemberService memberService; // MemberService 주입


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
            validateReview(reviewDTO, proveFile);

            // 검증 통과 후 리뷰 생성
            ReviewDTO createdReview = reviewService.createReview(reviewDTO, proveFile);
            return ResponseEntity.ok(createdReview);
        } catch (IllegalArgumentException e) {
            // 검증 실패 시 경고 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        }
    }

    // 검증 메서드
    private void validateReview(ReviewDTO reviewDTO, MultipartFile proveFile) {
        if (reviewDTO.getDepartmentId() == null) {
            throw new IllegalArgumentException("진료과를 선택해주세요.");
        }
        if (reviewDTO.getDoctorId() == null) {
            throw new IllegalArgumentException("의사를 선택해주세요.");
        }
        if (reviewDTO.getExplanationStars() == null) {
            throw new IllegalArgumentException("설명 항목에 별점을 선택해주세요.");
        }
        if (reviewDTO.getTreatmentResultStars() == null) {
            throw new IllegalArgumentException("치료 결과 항목에 별점을 선택해주세요.");
        }
        if (reviewDTO.getStaffKindnessStars() == null) {
            throw new IllegalArgumentException("친절 항목에 별점을 선택해주세요.");
        }
        if (reviewDTO.getCleanlinessStars() == null) {
            throw new IllegalArgumentException("청결 항목에 별점을 선택해주세요.");
        }
        if (reviewDTO.getContent() == null || reviewDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }
        if (proveFile == null || proveFile.isEmpty()) {
            throw new IllegalArgumentException("병원 방문을 인증할 자료를 업로드해주세요.");
        }
        if (reviewDTO.getMemberPassword() == null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
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
        return ResponseEntity.ok(reviewService.getReviewsWithPaginationAndAverages(page, fixedSize));
    }
}