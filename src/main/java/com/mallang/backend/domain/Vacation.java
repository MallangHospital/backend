package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자 유지
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 휴진 ID

    private LocalDate startDate; // 휴진 시작일
    private LocalDate endDate; // 휴진 종료일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // 해당 휴진을 설정하는 의사와 연결

    // 필요 시 추가적인 로직은 별도 메서드로 구현
}
