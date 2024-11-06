package com.mallang.backend.dto;

public class NewsDTO {
    private Long id;
    private String title;
    private String content;

    // 기본 생성자
    public NewsDTO() {}

    // 생성자
    public NewsDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}