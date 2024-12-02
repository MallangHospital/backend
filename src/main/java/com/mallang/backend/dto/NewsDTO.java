package com.mallang.backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long id;
    private String title;
    private String newsWriter;
    private String password;
    private String content;
    private String mainFileBase64; // Base64 이미지 데이터
    private String attachmentBase64; // Base64 첨부 파일 데이터
    private String regDate;
}