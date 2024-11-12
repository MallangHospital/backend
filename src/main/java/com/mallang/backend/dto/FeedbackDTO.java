package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
    private String title;
    private String content;
    private String name;
    private String phone;
    private String email;
}