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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    public ReviewDTO createReview(ReviewDTO reviewDTO, MultipartFile proveFile) {
        String filePath = saveFile(proveFile);


        Review review = convertToEntity(reviewDTO, filePath);
        review.setAverageStars(calculateAverageStars(review));
        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    // 리뷰 삭제
    public boolean deleteReview(Long id, String password, boolean isAdmin) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (isAdmin || review.getMemberPassword().equals(password)) {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

    // 페이지네이션과 평균 데이터 포함한 리뷰 조회
    public Map<String, Object> getReviewsWithPaginationAndAverages(int page, int size) {
        Page<Review> reviewsPage = reviewRepository.findAll(PageRequest.of(page - 1, size));

        // 리뷰 데이터
        List<ReviewDTO> reviews = reviewsPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 각 세부 항목의 평균 별점 계산
        double averageExplanationStars = reviewRepository.findAll().stream()
                .mapToInt(Review::getExplanationStars)
                .average()
                .orElse(0.0);

        double averageTreatmentResultStars = reviewRepository.findAll().stream()
                .mapToInt(Review::getTreatmentResultStars)
                .average()
                .orElse(0.0);

        double averageStaffKindnessStars = reviewRepository.findAll().stream()
                .mapToInt(Review::getStaffKindnessStars)
                .average()
                .orElse(0.0);

        double averageCleanlinessStars = reviewRepository.findAll().stream()
                .mapToInt(Review::getCleanlinessStars)
                .average()
                .orElse(0.0);

        // 전체 평균 계산
        double overallAverageStars = (averageExplanationStars + averageTreatmentResultStars +
                averageStaffKindnessStars + averageCleanlinessStars) / 4;

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("totalReviews", reviewsPage.getTotalElements());
        response.put("totalPages", reviewsPage.getTotalPages());
        response.put("averageExplanationStars", averageExplanationStars);
        response.put("averageTreatmentResultStars", averageTreatmentResultStars);
        response.put("averageStaffKindnessStars", averageStaffKindnessStars);
        response.put("averageCleanlinessStars", averageCleanlinessStars);
        response.put("overallAverageStars", overallAverageStars);

        return response;
    }

    private double calculateAverageStars(Review review) {
        return (review.getExplanationStars() + review.getTreatmentResultStars()
                + review.getStaffKindnessStars() + review.getCleanlinessStars()) / 4.0;
    }
    private Review convertToEntity(ReviewDTO reviewDTO, String filePath) {
        return Review.builder()
                .memberId(reviewDTO.getMemberId())
                .doctorId(reviewDTO.getDoctorId())
                .departmentId(reviewDTO.getDepartmentId())
                .explanationStars(reviewDTO.getExplanationStars())
                .treatmentResultStars(reviewDTO.getTreatmentResultStars())
                .staffKindnessStars(reviewDTO.getStaffKindnessStars())
                .cleanlinessStars(reviewDTO.getCleanlinessStars())
                .content(reviewDTO.getContent())
                .proveFilePath(filePath)
                .memberPassword(reviewDTO.getMemberPassword())
                .build();
    }


    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .doctorId(review.getDoctorId())
                .departmentId(review.getDepartmentId())
                .explanationStars(review.getExplanationStars())
                .treatmentResultStars(review.getTreatmentResultStars())
                .staffKindnessStars(review.getStaffKindnessStars())
                .cleanlinessStars(review.getCleanlinessStars())
                .averageStars(review.getAverageStars())
                .content(review.getContent())
                .build();
    }

    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/reviews/";
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                return filePath.toString();
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }
        return null;
    }
}