package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestPart("reviewDTO") String reviewDTOString,
            @RequestPart(value = "proveFile", required = false) MultipartFile proveFile,
            @AuthenticationPrincipal CustomMemberDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ReviewDTO reviewDTO = objectMapper.readValue(reviewDTOString, ReviewDTO.class);

            String validationMessage = validateReview(reviewDTO, proveFile);
            if (validationMessage != null) {
                return ResponseEntity.badRequest().body(validationMessage);
            }

            String memberId = userDetails.getUserId();
            ReviewDTO createdReview = reviewService.createReview(reviewDTO, proveFile, memberId);
            return ResponseEntity.ok(createdReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    private String validateReview(ReviewDTO reviewDTO, MultipartFile proveFile) {
        if (reviewDTO.getDepartmentId() == null) return "진료과를 선택해주세요.";
        if (reviewDTO.getDoctorId() == null) return "의사를 선택해주세요.";
        if (reviewDTO.getExplanationStars() == null) return "설명 항목에 별점을 선택해주세요.";
        if (reviewDTO.getTreatmentResultStars() == null) return "치료 결과 항목에 별점을 선택해주세요.";
        if (reviewDTO.getStaffKindnessStars() == null) return "친절 항목에 별점을 선택해주세요.";
        if (reviewDTO.getCleanlinessStars() == null) return "청결 항목에 별점을 선택해주세요.";
        if (reviewDTO.getExplanationStars() < 1 || reviewDTO.getExplanationStars() > 5) return "설명 항목의 별점은 1~5 사이여야 합니다.";
        if (reviewDTO.getTreatmentResultStars() < 1 || reviewDTO.getTreatmentResultStars() > 5) return "치료 결과 항목의 별점은 1~5 사이여야 합니다.";
        if (reviewDTO.getStaffKindnessStars() < 1 || reviewDTO.getStaffKindnessStars() > 5) return "친절 항목의 별점은 1~5 사이여야 합니다.";
        if (reviewDTO.getCleanlinessStars() < 1 || reviewDTO.getCleanlinessStars() > 5) return "청결 항목의 별점은 1~5 사이여야 합니다.";
        if (reviewDTO.getContent() == null || reviewDTO.getContent().trim().isEmpty()) return "리뷰 내용을 입력해주세요.";
        if (proveFile == null || proveFile.isEmpty()) return "병원 방문을 인증할 자료를 업로드해주세요.";
        if (reviewDTO.getMemberPassword() == null || reviewDTO.getMemberPassword().trim().isEmpty()) return "비밀번호를 입력해주세요.";
        return null;
    }

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

    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestParam(value = "page", defaultValue = "1") int page) {
        final int fixedSize = 10;
        Map<String, Object> response = reviewService.getReviewsWithPaginationAndAverages(page, fixedSize);
        return ResponseEntity.ok(response);
    }
}