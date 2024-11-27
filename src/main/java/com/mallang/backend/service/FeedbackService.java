package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // 모든 건의사항 조회
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // 특정 ID 건의사항 조회
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    // 건의사항 작성
    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
}