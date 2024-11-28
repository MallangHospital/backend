package com.mallang.backend.service;

import com.mallang.backend.domain.Feedback;
import com.mallang.backend.dto.FeedbackDTO;
import com.mallang.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // 모든 건의사항 조회
    @Transactional(readOnly = true)
    public List<FeedbackDTO> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 ID 건의사항 조회
    @Transactional(readOnly = true)
    public FeedbackDTO getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // 건의사항 작성
    @Transactional
    public FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = convertToEntity(feedbackDTO);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDTO(savedFeedback);
    }

    // DTO -> Entity 변환
    private Feedback convertToEntity(FeedbackDTO dto) {
        return Feedback.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }

    // Entity -> DTO 변환
    private FeedbackDTO convertToDTO(Feedback entity) {
        return FeedbackDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .build();
    }
}