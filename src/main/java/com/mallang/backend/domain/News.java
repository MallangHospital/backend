package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 뉴스 ID

    private String title;            // 뉴스 제목
    private String content;          // 뉴스 내용
    private LocalDate writeDate;     // 작성 날짜

    // 기본 생성자에서 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // title과 content만 받는 생성자 추가
    public News(String title, String content) {
        this.title = title;
        this.content = content;
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}