package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackDTO {
    private Long id;
    private String title;
    private String content;
    private String name;
    private String phoneNumber;
    private String email;
}