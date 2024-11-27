package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // 건의사항 등록
    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // 모든 건의사항 조회 (관리자 전용)
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll(); // 모든 건의사항 조회
    }

}