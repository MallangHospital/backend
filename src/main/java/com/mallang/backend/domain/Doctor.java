package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor // 기본 생성자 필요
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 추가
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String name;
    private String position;

    @ElementCollection(fetch = FetchType.EAGER) // 컬렉션을 매핑할 때 사용 (즉시 로딩 설정)
    @CollectionTable(name = "doctor_history", joinColumns = @JoinColumn(name = "doctor_id")) // 별도 테이블로 매핑
    @Column(name = "history_item") // 테이블의 컬럼명 설정
    private List<String> history = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // Department와의 관계 설정 (다대일)
    @JoinColumn(name = "department_id") // 외래키 설정
    private Department department;
}