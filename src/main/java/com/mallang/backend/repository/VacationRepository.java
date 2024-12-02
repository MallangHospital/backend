package com.mallang.backend.repository;

import com.mallang.backend.domain.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("SELECT COUNT(v) > 0 FROM Vacation v WHERE v.doctor.id = :doctorId AND :today BETWEEN v.startDate AND v.endDate")
    boolean isDoctorOnVacation(@Param("doctorId") Long doctorId, @Param("today") LocalDate today);
}