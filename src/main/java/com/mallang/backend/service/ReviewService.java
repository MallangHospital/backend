package com.mallang.backend.service;

import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final Path uploadDir = Paths.get("uploads");

    // 리뷰 페이지네이션 조회
    public Page<ReviewDTO> getReviewsWithPagination(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size)).map(this::convertToDTO);
    }

    // 특정 의사에 대한 리뷰 조회
    public Page<ReviewDTO> getReviewsByDoctorWithPagination(Long doctorId, int page, int size) {
        return reviewRepository.findByDoctorId(doctorId, PageRequest.of(page, size)).map(this::convertToDTO);
    }

    // 리뷰 작성
    public ReviewDTO createReview(ReviewDTO reviewDTO, MultipartFile receiptFile) {
        Review review = new Review();
        review.setMemberId(reviewDTO.getMemberId());
        review.setDoctorId(reviewDTO.getDoctorId());
        review.setDepartmentId(reviewDTO.getDepartmentId());
        review.setStar(reviewDTO.getStar());
        review.setDetailStars(reviewDTO.getDetailStars());
        review.setContent(reviewDTO.getContent());
        review.setMemberPassword(reviewDTO.getMemberPassword());

        // 파일 저장
        if (receiptFile != null && !receiptFile.isEmpty()) {
            String filePath = saveFile(receiptFile);
            review.setReceiptFilePath(filePath);
        }

        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    // 리뷰 수정
    public boolean updateReview(Long id, ReviewDTO updatedReviewDTO) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            if (!review.getMemberPassword().equals(updatedReviewDTO.getMemberPassword())) {
                return false; // 비밀번호 불일치
            }
            review.setStar(updatedReviewDTO.getStar());
            review.setDetailStars(updatedReviewDTO.getDetailStars());
            review.setContent(updatedReviewDTO.getContent());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    // 리뷰 삭제
    public boolean deleteReview(Long id, String password) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            // 관리자는 비밀번호 없이 삭제 가능
            if (password == null || review.getMemberPassword().equals(password)) {
                reviewRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    private String saveFile(MultipartFile file) {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetLocation = uploadDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation);

            return targetLocation.toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage(), e);
        }
    }

    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .doctorId(review.getDoctorId())
                .departmentId(review.getDepartmentId())
                .star(review.getStar())
                .detailStars(review.getDetailStars())
                .content(review.getContent())
                .createdAt(review.getCreatedDate())
                .receiptFilePath(review.getReceiptFilePath() != null ? "인증 완료" : "인증 없음")
                .build();
    }
}