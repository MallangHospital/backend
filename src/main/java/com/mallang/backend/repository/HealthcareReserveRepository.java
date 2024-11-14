package com.mallang.backend.repository;

import com.mallang.backend.domain.HealthcareReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthcareReserveRepository extends JpaRepository<HealthcareReserve, Long> {
}