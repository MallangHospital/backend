package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자만 사용
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String name; // 의료진 이름
    private String position; // 전문 분야
    private String phoneNumber; // 연락처
    private String photoUrl; // 의료진 사진 URL
    private LocalDate localDate; // 휴진 시작일
    private LocalDate vacationEndDate; // 휴진 종료일

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doctor_history", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "history_item")
    private List<String> history = new ArrayList<>(); // 경력 목록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department; // 의료진이 소속된 부서

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacations = new ArrayList<>(); // 의사와 연결된 휴진 정보 목록

    // 헬퍼 메서드
    public void addVacation(Vacation vacation) {
        this.vacations.add(vacation);
        vacation.setDoctor(this);
    }

    public void addHistoryItem(String historyItem) {
        this.history.add(historyItem);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", localDate=" + localDate +
                ", vacationEndDate=" + vacationEndDate +
                '}';
    }
}
