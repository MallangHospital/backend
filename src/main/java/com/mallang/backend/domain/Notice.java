package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notices")
@Getter
@Setter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 공지사항 ID

    private String title;            // 공지사항 제목
    private String content;          // 공지사항 내용
    private LocalDate writeDate;     // 작성 날짜

    // @PrePersist를 사용하여 writeDate 초기화
    @PrePersist
    protected void onCreate() {
        this.writeDate = LocalDate.now(); // 현재 날짜로 초기화
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}