package com.mallang.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDTO {
    private String id;
    private String title;         // 제목
    private String content;     // 내용
    private String author;      // 작성자
    private String password;    // 비밀번호

    private String writeDate;   // 작성 날짜 (LocalDate -> String으로 처리)
}