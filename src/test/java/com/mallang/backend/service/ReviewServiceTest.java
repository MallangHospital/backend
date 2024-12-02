/*
package com.mallang.backend.service;

import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReviewTest() {
        // Mock 입력 데이터
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .doctorId(1L)
                .departmentId(2L)
                .explanationStars(5)
                .treatmentResultStars(4)
                .staffKindnessStars(5)
                .cleanlinessStars(4)
                .content("Great service!")
                .memberPassword("password123")
                .build();

        MockMultipartFile proveFile = new MockMultipartFile(
                "proveFile", "test.jpg", "image/jpeg", "test image content".getBytes()
        );

        // Mock Review 저장 결과
        Review mockReview = Review.builder()
                .id(1L)
                .doctorId(1L)
                .departmentId(2L)
                .explanationStars(5)
                .treatmentResultStars(4)
                .staffKindnessStars(5)
                .cleanlinessStars(4)
                .content("Great service!")
                .memberPassword("password123")
                .memberId("testUser")
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);

        // 테스트 실행
        ReviewDTO result = reviewService.createReview(reviewDTO, proveFile, "testUser");

        // 결과 검증
        assertNotNull(result);
        assertEquals(1L, result.getDoctorId());
        assertEquals(2L, result.getDepartmentId());
        assertEquals("Great service!", result.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    public boolean deleteReview(Long id, String password, boolean isAdmin) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            if (isAdmin || review.getMemberPassword().equals(password)) {
                reviewRepository.delete(review);
                return true;
            }
        } else {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }

        return false;
    }

    @Test
    void getReviewsWithPaginationAndAveragesTest() {
        // 테스트 데이터
        when(reviewRepository.findAll()).thenReturn(List.of(
                Review.builder().explanationStars(5).treatmentResultStars(4).staffKindnessStars(5).cleanlinessStars(4).build(),
                Review.builder().explanationStars(4).treatmentResultStars(5).staffKindnessStars(4).cleanlinessStars(5).build()
        ));

        when(reviewRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(Page.empty()); // 빈 페이지로 시뮬레이션

        // 테스트 실행
        Map<String, Object> result = reviewService.getReviewsWithPaginationAndAverages(1, 10);

        // 검증
        assertNotNull(result);
        assertEquals(4.5, result.get("averageExplanationStars"));
        assertEquals(4.5, result.get("averageTreatmentResultStars"));
        assertEquals(4.5, result.get("averageStaffKindnessStars"));
        assertEquals(4.5, result.get("averageCleanlinessStars"));
        assertTrue(result.containsKey("reviews"));
    }
}

 */