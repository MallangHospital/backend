package com.mallang.backend.repository;

import com.mallang.backend.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // 특정 회원 ID로 예약 조회
    List<Appointment> findByMember_Mid(String memberId);
}