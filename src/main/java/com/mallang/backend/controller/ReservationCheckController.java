package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.service.AppointmentService;
import com.mallang.backend.service.HealthcareReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/my-reservations")
@RequiredArgsConstructor
public class ReservationCheckController {

    private final AppointmentService appointmentService;
    private final HealthcareReserveService healthcareReserveService;

    // 접수확인
    @GetMapping
    public ResponseEntity<Map<String, List<?>>> getAllReservations(@AuthenticationPrincipal CustomMemberDetails userDetails) {
        String memberId = userDetails.getUserId();

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByMemberId(memberId);
        List<HealthcareReserveDTO> healthChecks = healthcareReserveService.getHealthReservesByMemberId(memberId);

        Map<String, List<?>> reservations = new HashMap<>();
        reservations.put("appointments", appointments);
        reservations.put("healthChecks", healthChecks);

        return ResponseEntity.ok(reservations);
    }

    // 진료 예약 취소
    @DeleteMapping("/appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment canceled successfully.");
    }

    // 건강검진 예약 취소
    @DeleteMapping("/health-check/{id}")
    public ResponseEntity<String> cancelHealthCheck(@PathVariable Long id) {
        healthcareReserveService.cancelHealthCheck(id);
        return ResponseEntity.ok("Health check reservation canceled successfully.");
    }

    // 진료 예약 상세 조회
    @GetMapping("/appointment/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    // 건강검진 예약 상세 조회
    @GetMapping("/health-check/{id}")
    public ResponseEntity<HealthcareReserveDTO> getHealthCheckDetails(@PathVariable Long id) {
        return ResponseEntity.ok(healthcareReserveService.getHealthcareReserveById(id));
    }
}