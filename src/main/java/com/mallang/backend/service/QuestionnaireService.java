package com.mallang.backend.service;

import com.mallang.backend.domain.Question;
import com.mallang.backend.domain.Response;
import com.mallang.backend.dto.QuestionDTO;
import com.mallang.backend.dto.ResponseDTO;
import com.mallang.backend.repository.QuestionRepository;
import com.mallang.backend.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    // 모든 질문 조회
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(question -> QuestionDTO.builder()
                        .id(question.getId())
                        .questionText(question.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    // 응답 저장
    public void saveResponsesFromDTOs(String memberId, List<ResponseDTO> responseDTOs) {
        List<Response> responses = responseDTOs.stream()
                .map(dto -> Response.builder()
                        .memberId(memberId)
                        .questionId(dto.getQuestionId())
                        .answer(String.valueOf(dto.getAnswer()))
                        .build())
                .collect(Collectors.toList());

        responseRepository.saveAll(responses);
    }

    // 특정 사용자의 응답 조회
    public List<ResponseDTO> getResponses(String memberId) {
        return responseRepository.findByMemberId(memberId).stream()
                .map(response -> ResponseDTO.builder()
                        .questionId(response.getQuestionId())
                        .answer(Integer.valueOf(response.getAnswer()))
                        .build())
                .collect(Collectors.toList());
    }
}