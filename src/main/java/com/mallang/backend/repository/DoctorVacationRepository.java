package com.mallang.backend.repository;

import com.mallang.backend.domain.DoctorVacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorVacationRepository extends JpaRepository<DoctorVacation, Long> {
}