package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
}