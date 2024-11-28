package com.mallang.backend.repository;

import com.mallang.backend.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // 특정 부서에 속한 의사 목록 조회
    List<Doctor> findByDepartmentId(Long departmentId);

    // 이름으로 의사 조회 (추가적인 검색 조건 예시)
    List<Doctor> findByNameContaining(String name);

    // 특정 전문 분야를 가진 의사 조회
    List<Doctor> findBySpecialty(String specialty);
}