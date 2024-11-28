package com.mallang.backend.controller;

import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.service.HealthcareReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthcare-reserves")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 관리자 전용
public class HealthcareReserveController {

    private final HealthcareReserveService healthcareReserveService;

    // 모든 건강검진 예약 정보 조회
    @GetMapping
    public ResponseEntity<List<HealthcareReserveDTO>> getAllHealthcareReserves() {
        return ResponseEntity.ok(healthcareReserveService.getAllHealthReserves());
    }

    // 특정 건강검진 예약 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<HealthcareReserveDTO> getHealthcareReserveDetails(@PathVariable Long id) {
        return ResponseEntity.ok(healthcareReserveService.getHealthcareReserveById(id));
    }

    // 건강검진 예약 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelHealthcareReserve(@PathVariable Long id) {
        healthcareReserveService.cancelHealthCheck(id);
        return ResponseEntity.ok("건강검진 예약이 취소되었습니다.");
    }
}