/*
package com.mallang.backend.service;

import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview() throws Exception {
        // Given
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMemberId(1L);
        reviewDTO.setDoctorId(1L);
        reviewDTO.setDepartmentId(1L);
        reviewDTO.setDetailStar(List.of(5, 4, 4, 5));
        reviewDTO.setContent("Great service!");
        reviewDTO.setMemberPassword("12345");

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.png", "image/png", "test image content".getBytes()
        );

        // Mock 파일 경로
        Path mockFilePath = Paths.get("uploads/test.png");

        // 파일이 존재하면 삭제
        if (Files.exists(mockFilePath)) {
            Files.delete(mockFilePath);
        }

        // 디렉터리 생성
        Files.createDirectories(mockFilePath.getParent());

        // Mock repository 동작 설정
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        reviewService.createReview(reviewDTO, file);

        // Then
        verify(reviewRepository, times(1)).save(any(Review.class));

        // 테스트 후 파일 삭제
        Files.deleteIfExists(mockFilePath);
    }

    @Test
    void testGetAllReviews() {
        // Given
        Review review = new Review();
        review.setId(1L);
        review.setMemberId(1L);
        review.setDoctorId(1L);
        review.setDepartmentId(1L);
        review.setDetailStars(List.of(5, 4, 4, 5));
        review.setContent("Great service!");

        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // When
        List<ReviewDTO> reviews = reviewService.getAllReviews();

        // Then
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(1L, reviews.get(0).getId());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testDeleteReviewById() {
        // Given
        Long reviewId = 1L;

        // When
        reviewService.deleteReviewById(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testIsValidDepartment() {
        // Given
        String departmentName = "Cardiology";
        when(departmentRepository.findByName(departmentName)).thenReturn((Optional<Department>) new Object());

        // When
        boolean result = reviewService.isValidDepartment(departmentName);

        // Then
        assertTrue(result);
        verify(departmentRepository, times(1)).findByName(departmentName);
    }

    @Test
    void testIsValidDoctor() {
        // Given
        Long doctorId = 1L;
        when(doctorRepository.existsById(doctorId)).thenReturn(true);

        // When
        boolean result = reviewService.isValidDoctor(doctorId);

        // Then
        assertTrue(result);
        verify(doctorRepository, times(1)).existsById(doctorId);
    }

    @Test
    void testCalculateReviewStatistics() {
        // Given
        Review review1 = new Review();
        review1.setDetailStars(List.of(5, 4, 3, 5));

        Review review2 = new Review();
        review2.setDetailStars(List.of(4, 3, 5, 4));

        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // When
        Map<String, Object> statistics = reviewService.calculateReviewStatistics();

        // Then
        assertNotNull(statistics);
        assertEquals(2L, statistics.get("totalReviewCount"));
        assertTrue((double) statistics.get("overallRatingAverage") > 0);
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testCalculateDetailAverage() {
        // Given
        Review review1 = new Review();
        review1.setDetailStars(List.of(5, 4, 3, 5));

        Review review2 = new Review();
        review2.setDetailStars(List.of(4, 3, 5, 4));

        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // When
        double average = reviewService.calculateDetailAverage("자세한 설명");

        // Then
        assertEquals(4.5, average, 0.01); // 두 리뷰의 첫 번째 별점 평균
        verify(reviewRepository, times(1)).findAll();
    }
}

 */