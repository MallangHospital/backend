package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@NoArgsConstructor // 기본 생성자만 사용
@Entity
@Table(name = "department") // 매핑할 테이블 이름
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpid") // 데이터베이스의 실제 컬럼 이름과 매핑
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Doctor> doctors = new ArrayList<>();

    // 헬퍼 메서드
    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setDepartment(this);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}