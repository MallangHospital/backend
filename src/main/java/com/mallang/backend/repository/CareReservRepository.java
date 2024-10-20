package com.mallang.backend.repository;

import com.mallang.backend.domain.CareReserv;
import com.mallang.backend.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CareReservRepository extends JpaRepository<CareReserv, Long> {
    CareReserv findByAppointmentDateTimeAndDoctor(LocalDateTime appointmentDateTime, Doctor doctor);
}
