package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자만 유지
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 뉴스 ID

    private String title; // 뉴스 제목
    private String name; // 작성자 이름
    private String password; // 비밀번호

    @Column(name = "attachment1")
    private String attachment1; // 첨부파일 1 URL

    @Column(name = "attachment2")
    private String attachment2; // 첨부파일 2 URL

    private String content; // 뉴스 내용



    private LocalDate writeDate; // 작성 날짜

    // 작성 날짜 초기화
    @PrePersist
    protected void onCreate() {
        this.writeDate = LocalDate.now();
    }

    // 내용 업데이트 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", attachment1='" + attachment1 + '\'' +
                ", attachment2='" + attachment2 + '\'' +
                ", content='" + content + '\'' +
                ", writeDate=" + writeDate +
                '}';
    }
}