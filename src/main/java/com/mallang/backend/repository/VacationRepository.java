package com.mallang.backend.repository;

import com.mallang.backend.domain.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}