/*
package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.dto.FeedbackDTO;
import com.mallang.backend.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFeedback_ShouldReturnFeedbackList() {
        // Arrange
        Feedback feedback1 = Feedback.builder()
                .id(1L)
                .title("Feedback 1")
                .content("Content 1")
                .name("User 1")
                .phoneNumber("010-1111-1111")
                .email("user1@example.com")
                .build();

        Feedback feedback2 = Feedback.builder()
                .id(2L)
                .title("Feedback 2")
                .content("Content 2")
                .name("User 2")
                .phoneNumber("010-2222-2222")
                .email("user2@example.com")
                .build();

        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback1, feedback2));

        // Act
        List<FeedbackDTO> feedbackList = feedbackService.getAllFeedback();

        // Assert
        assertEquals(2, feedbackList.size());
        assertEquals("Feedback 1", feedbackList.get(0).getTitle());
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void getFeedbackById_ShouldReturnFeedback_WhenExists() {
        // Arrange
        Feedback feedback = Feedback.builder()
                .id(1L)
                .title("Feedback 1")
                .content("Content 1")
                .name("User 1")
                .phoneNumber("010-1111-1111")
                .email("user1@example.com")
                .build();

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        // Act
        FeedbackDTO feedbackDTO = feedbackService.getFeedbackById(1L);

        // Assert
        assertNotNull(feedbackDTO);
        assertEquals("Feedback 1", feedbackDTO.getTitle());
        verify(feedbackRepository, times(1)).findById(1L);
    }

    @Test
    void getFeedbackById_ShouldReturnNull_WhenNotExists() {
        // Arrange
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        FeedbackDTO feedbackDTO = feedbackService.getFeedbackById(1L);

        // Assert
        assertNull(feedbackDTO);
        verify(feedbackRepository, times(1)).findById(1L);
    }

    @Test
    void submitFeedback_ShouldSaveFeedback() {
        // Arrange
        FeedbackDTO feedbackDTO = FeedbackDTO.builder()
                .title("New Feedback")
                .content("New Content")
                .name("New User")
                .phoneNumber("010-3333-3333")
                .email("newuser@example.com")
                .build();

        Feedback feedback = Feedback.builder()
                .id(1L)
                .title("New Feedback")
                .content("New Content")
                .name("New User")
                .phoneNumber("010-3333-3333")
                .email("newuser@example.com")
                .build();

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        // Act
        FeedbackDTO savedFeedback = feedbackService.submitFeedback(feedbackDTO);

        // Assert
        assertNotNull(savedFeedback);
        assertEquals("New Feedback", savedFeedback.getTitle());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }
}

 */