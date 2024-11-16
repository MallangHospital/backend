package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Doctor와의 관계 설정 (일대다)
    private List<Doctor> doctors = new ArrayList<>();

    // 기본 생성자는 @NoArgsConstructor가 제공되므로 생략
    // name 필드만 설정하는 생성자는 @RequiredArgsConstructor로 자동 제공
}