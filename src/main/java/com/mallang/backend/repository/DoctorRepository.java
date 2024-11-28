package com.mallang.backend.repository;

import com.mallang.backend.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // 필요 시 추가적인 커스텀 쿼리 메서드 정의
}