package com.mallang.backend.repository;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByMember_Mid(String memberId);
    List<Appointment> findByMember(Member member);
}