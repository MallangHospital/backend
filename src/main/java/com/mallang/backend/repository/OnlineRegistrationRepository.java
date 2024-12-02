package com.mallang.backend.repository;

import com.mallang.backend.domain.OnlineRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnlineRegistrationRepository extends JpaRepository<OnlineRegistration, Long> {
    // 특정 의사에 대한 접수 건수 조회
    // doctorId의 id 값을 기준으로 조회하도록 쿼리 작성
    @Query("SELECT COUNT(r) FROM OnlineRegistration r WHERE r.doctorId.id = :doctorId")
    long countByDoctorId(@Param("doctorId") Long doctorId);
}