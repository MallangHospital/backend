package com.mallang.backend.service;

import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         DepartmentRepository departmentRepository,
                         DoctorRepository doctorRepository) {
        this.reviewRepository = reviewRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    // 리뷰 생성
    @Transactional
    public void createReview(ReviewDTO reviewDTO, MultipartFile file) {
        validateReviewDTO(reviewDTO);

        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                fileUrl = saveFile(file);
            } catch (RuntimeException ex) {
                throw new IllegalStateException("파일 저장 실패: " + ex.getMessage(), ex);
            }
        }

        saveReview(reviewDTO, fileUrl);
    }

    // 모든 리뷰 조회 (페이징 지원)
    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(this::convertToDTO);
    }

    // 리뷰 저장
    private void saveReview(ReviewDTO reviewDTO, String fileUrl) {
        Review review = Review.builder()
                .memberId(reviewDTO.getMemberId())
                .doctorId(reviewDTO.getDoctorId())
                .departmentId(reviewDTO.getDepartmentId())
                .departmentName(reviewDTO.getDepartmentName())
                .doctorName(reviewDTO.getDoctorName())
                .memberName(reviewDTO.getMemberName())
                .detailStars(reviewDTO.getDetailStar())
                .content(reviewDTO.getContent())
                .fileUrl(fileUrl)
                .memberPassword(reviewDTO.getMemberPassword())
                .build();

        reviewRepository.save(review);
    }

    // 리뷰 DTO 검증
    private void validateReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO.getDepartmentId() == null || !isValidDepartment(reviewDTO.getDepartmentId())) {
            throw new IllegalArgumentException("유효하지 않은 진료과 ID입니다: " + reviewDTO.getDepartmentId());
        }
        if (reviewDTO.getDoctorId() == null || !isValidDoctor(reviewDTO.getDoctorId())) {
            throw new IllegalArgumentException("유효하지 않은 의사 ID입니다: " + reviewDTO.getDoctorId());
        }
        if (reviewDTO.getMemberId() == null) {
            throw new IllegalArgumentException("Member ID는 필수입니다.");
        }
        if (reviewDTO.getMemberPassword() == null || reviewDTO.getMemberPassword().isEmpty()) {
            throw new IllegalArgumentException("Member Password는 필수입니다.");
        }
        if (reviewDTO.getContent() == null || reviewDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용은 필수입니다.");
        }
    }

    // 특정 진료과 ID 유효성 확인
    public boolean isValidDepartment(Long departmentId) {
        return reviewRepository.existsByDepartmentId(departmentId);
    }

    // 특정 의사 ID 유효성 확인
    public boolean isValidDoctor(Long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    // 파일 저장
    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            Path uploadDir = Paths.get("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

            Path targetLocation = uploadDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation);

            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("파일 업로드 실패: " + ex.getMessage(), ex);
        }
    }

    // 엔티티 -> DTO 변환
    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .doctorId(review.getDoctorId())
                .departmentId(review.getDepartmentId())
                .departmentName(review.getDepartmentName())
                .doctorName(review.getDoctorName())
                .memberName(review.getMemberName())
                .detailStar(review.getDetailStars())
                .content(review.getContent())
                .attachment(review.getFileUrl())
                .createdAt(review.getCreatedDate())
                .build();
    }
}