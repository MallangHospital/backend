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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewsWithPagination() {
        // Given
        Review review = Review.builder()
                .id(1L)
                .memberId(1L)
                .doctorId(2L)
                .departmentId(3L)
                .detailStars(List.of(5, 4, 5))
                .content("Great service")
                .build();
        Page<Review> reviewPage = new PageImpl<>(List.of(review));
        when(reviewRepository.findAll(PageRequest.of(0, 10))).thenReturn(reviewPage);

        // When
        Page<ReviewDTO> result = reviewService.getReviewsWithPagination(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Great service", result.getContent().get(0).getContent());
        verify(reviewRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetReviewsByDoctorWithPagination() {
        // Given
        Review review = Review.builder()
                .id(1L)
                .memberId(1L)
                .doctorId(2L)
                .departmentId(3L)
                .detailStars(List.of(5, 4, 5))
                .content("Excellent doctor")
                .build();
        Page<Review> reviewPage = new PageImpl<>(List.of(review));
        when(reviewRepository.findByDoctorId(2L, PageRequest.of(0, 10))).thenReturn(reviewPage);

        // When
        Page<ReviewDTO> result = reviewService.getReviewsByDoctorWithPagination(2L, 0, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Excellent doctor", result.getContent().get(0).getContent());
        verify(reviewRepository, times(1)).findByDoctorId(2L, PageRequest.of(0, 10));
    }

    @Test
    void testCreateReview() {
        // Given
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .memberId(1L)
                .doctorId(2L)
                .departmentId(3L)
                .detailStars(List.of(5, 5, 5))
                .content("Amazing experience!")
                .memberPassword("password123")
                .build();

        Review review = Review.builder()
                .id(1L)
                .memberId(1L)
                .doctorId(2L)
                .departmentId(3L)
                .detailStars(List.of(5, 5, 5))
                .content("Amazing experience!")
                .memberPassword("password123")
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        ReviewDTO result = reviewService.createReview(reviewDTO, null);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Amazing experience!", result.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testUpdateReview() {
        // Given
        Long reviewId = 1L;
        Review existingReview = Review.builder()
                .id(reviewId)
                .content("Old content")
                .detailStars(List.of(3, 3, 3))
                .memberPassword("password123")
                .build();

        ReviewDTO updateDTO = ReviewDTO.builder()
                .content("Updated content")
                .detailStars(List.of(4, 4, 4))
                .memberPassword("password123")
                .build();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));

        // When
        boolean result = reviewService.updateReview(reviewId, updateDTO);

        // Then
        assertTrue(result);
        assertEquals("Updated content", existingReview.getContent());
        assertEquals(List.of(4, 4, 4), existingReview.getDetailStars());
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    void testDeleteReview() {
        // Given
        Long reviewId = 1L;
        Review review = Review.builder().id(reviewId).memberPassword("password123").build();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // When
        boolean result = reviewService.deleteReview(reviewId, "password123");

        // Then
        assertTrue(result);
        verify(reviewRepository, times(1)).delete(review);

    }
}*/