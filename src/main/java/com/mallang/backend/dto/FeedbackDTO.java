package com.mallang.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private Long id;
    private String title;
    private String content;
    private String name;
    private String phoneNumber;
    private String email;
    private String regDate;
}