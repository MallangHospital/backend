package com.mallang.backend.controller;

import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 모든 리뷰 조회 (로그인하지 않은 사용자도 접근 가능)
    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestBody ReviewDTO reviewFilterDTO) {
        return ResponseEntity.ok(reviewService.getReviewsWithPagination(reviewFilterDTO.getPage(), reviewFilterDTO.getSize()));
    }

    // 특정 의사에 대한 리뷰 조회 (로그인하지 않은 사용자도 접근 가능)
    @GetMapping("/doctor")
    public ResponseEntity<?> getReviewsByDoctor(@RequestBody ReviewDTO reviewFilterDTO) {
        return ResponseEntity.ok(reviewService.getReviewsByDoctorWithPagination(
                reviewFilterDTO.getDoctorId(),
                reviewFilterDTO.getPage(),
                reviewFilterDTO.getSize()
        ));
    }

    // 리뷰 작성 (진료기록이 있는 사용자만 가능)
    @PostMapping
    @PreAuthorize("@customSecurityService.hasMedicalRecord(authentication.name)")
    public ResponseEntity<?> createReview(
            @RequestPart ReviewDTO reviewDTO,
            @RequestPart(required = false) MultipartFile receiptFile
    ) {
        ReviewDTO savedReview = reviewService.createReview(reviewDTO, receiptFile);
        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다! ID: " + savedReview.getId());
    }

    // 리뷰 수정 (작성자 본인만 가능, 비밀번호 필요)
    @PutMapping("/{id}")
    @PreAuthorize("@customSecurityService.isReviewOwner(authentication.name, #id)")
    public ResponseEntity<?> updateReview(@PathVariable String id, @RequestBody ReviewDTO reviewDTO) {
        boolean isUpdated = reviewService.updateReview(id, reviewDTO);
        if (isUpdated) {
            return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("리뷰 수정에 실패하였습니다. 비밀번호를 확인해주세요.");
        }
    }

    // 리뷰 삭제 (작성자는 비밀번호 필요, 관리자는 비밀번호 없이 가능)
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityService.isReviewOwnerOrAdmin(authentication.name, #id)")
    public ResponseEntity<?> deleteReview(@PathVariable String id, @RequestBody ReviewDTO reviewDTO) {
        boolean isDeleted = reviewService.deleteReview(id, reviewDTO.getMemberPassword());
        if (isDeleted) {
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("리뷰 삭제에 실패하였습니다. 비밀번호를 확인해주세요.");
        }
    }

    // 입력 데이터 검증 (리뷰 작성 시)
    private void validateReviewData(ReviewDTO reviewDTO, MultipartFile receiptFile) {
        if (reviewDTO.getDepartmentId() == null || reviewDTO.getDepartmentId().isEmpty()) {
            throw new IllegalArgumentException("진료과를 선택해주세요.");
        }
        if (reviewDTO.getDoctorId() == null || reviewDTO.getDoctorId().isEmpty()) {
            throw new IllegalArgumentException("의사를 선택해주세요.");
        }
        if (reviewDTO.getDetailStars() == null || reviewDTO.getDetailStars().isEmpty()) {
            throw new IllegalArgumentException("별점을 선택해주세요.");
        }
        if (reviewDTO.getContent() == null || reviewDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }
        if (receiptFile == null || receiptFile.isEmpty()) {
            throw new IllegalArgumentException("병원 방문을 인증할 자료를 업로드해주세요.");
        }
    }

    // 입력 데이터 검증 (리뷰 수정 시)
    private void validateReviewUpdateData(ReviewDTO reviewDTO) {
        if (reviewDTO.getDetailStars() == null || reviewDTO.getDetailStars().isEmpty()) {
            throw new IllegalArgumentException("별점을 선택해주세요.");
        }
        if (reviewDTO.getContent() == null || reviewDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }
    }
}