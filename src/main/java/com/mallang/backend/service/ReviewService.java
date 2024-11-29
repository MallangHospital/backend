package com.mallang.backend.service;

import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 페이지네이션 조회
    public Page<ReviewDTO> getReviewsWithPagination(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size)).map(this::convertToDTO);
    }

    // 특정 의사에 대한 리뷰 조회
    public Page<ReviewDTO> getReviewsByDoctorWithPagination(String doctorName, int page, int size) {
        return reviewRepository.findByDoctorName(doctorName, PageRequest.of(page, size)).map(this::convertToDTO);
    }

    // 리뷰 작성
    public ReviewDTO createReview(ReviewDTO reviewDTO, MultipartFile receiptFile) {
        // 파일 저장 처리 로직
        String filePath = saveFile(receiptFile);

        Review review = convertToEntity(reviewDTO, filePath);
        Review savedReview = reviewRepository.save(review);

        return convertToDTO(savedReview);
    }

    // 리뷰 수정
    public boolean updateReview(String id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getMemberPassword().equals(reviewDTO.getMemberPassword())) {
            return false;
        }

        review.setContent(reviewDTO.getContent());
        review.setDetailStars(reviewDTO.getDetailStars());
        reviewRepository.save(review);

        return true;
    }

    // 리뷰 삭제
    public boolean deleteReview(String id, String password) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (password == null || review.getMemberPassword().equals(password)) {
            reviewRepository.delete(review);
            return true;
        }

        return false;
    }

    // 엔티티 변환
    private Review convertToEntity(ReviewDTO reviewDTO, String filePath) {
        return Review.builder()
                .memberId(reviewDTO.getMemberId())         // 작성자 ID
                .doctorName(reviewDTO.getDoctorName())     // 의사 이름
                .departmentName(reviewDTO.getDepartmentName()) // 진료과 이름
                .detailStars(reviewDTO.getDetailStars())   // 세부 별점 리스트
                .content(reviewDTO.getContent())           // 리뷰 본문
                .receiptFilePath(filePath)                 // 영수증 파일 경로
                .memberPassword(reviewDTO.getMemberPassword()) // 작성자 비밀번호
                .build();
    }

    // DTO 변환
    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .doctorName(review.getDoctorName())         // 의사 이름
                .departmentName(review.getDepartmentName()) // 진료과 이름
                .detailStars(review.getDetailStars())
                .content(review.getContent())
                .file(review.getReceiptFilePath())
                .memberPassword(review.getMemberPassword())
                .build();
    }

    // 파일 저장 처리 로직 (필요시 구현)
    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            // 파일 저장 로직
            return "path/to/saved/file";
        }
        return null;
    }
}