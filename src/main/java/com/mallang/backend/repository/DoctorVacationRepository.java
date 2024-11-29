package com.mallang.backend.repository;

import com.mallang.backend.domain.DoctorVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorVacationRepository extends JpaRepository<DoctorVacation, Long> {
    List<DoctorVacation> findByDoctorId(Long doctorId);
}