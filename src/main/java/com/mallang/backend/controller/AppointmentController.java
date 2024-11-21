package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.domain.Member;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(
            @AuthenticationPrincipal CustomMemberDetails userDetails, // CustomMemberDetails 주입
            @RequestBody AppointmentDTO appointmentDTO) {

        System.out.println("Received DTO: " + appointmentDTO);

        // CustomMemberDetails에서 Member 객체를 가져옴
        Member member = userDetails.getMember();
        appointmentDTO.setMemberId(member.getMid()); // Member ID 설정

        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok(createdAppointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAppointments(
            @AuthenticationPrincipal String memberId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByMemberId(memberId);
        return ResponseEntity.ok(appointments);
    }
}