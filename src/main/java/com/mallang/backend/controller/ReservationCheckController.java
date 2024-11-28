package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my-reservations") // 예약 조회를 위한 명확한 경로 설정
@RequiredArgsConstructor
public class ReservationCheckController {

    private final AppointmentService appointmentService;

    // 진료 예약 조회
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAppointments(@AuthenticationPrincipal CustomMemberDetails userDetails) {
        String memberId = userDetails.getUserId();

        // 회원의 진료 예약 조회
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByMemberId(memberId);

        return ResponseEntity.ok(appointments);
    }
}