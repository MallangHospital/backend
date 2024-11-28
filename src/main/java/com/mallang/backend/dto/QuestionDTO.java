package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionDTO {
    private Long id; // 질문 ID
    private String questionText; // 질문 텍스트
}