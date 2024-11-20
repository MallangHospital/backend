package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createAppointment(
            @AuthenticationPrincipal CustomMemberDetails userDetails,
            @RequestBody AppointmentDTO appointmentDTO) {

        System.out.println(appointmentDTO.getDoctorId());
        String memberId = userDetails.getUserId();
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO, memberId);
        return ResponseEntity.ok().body(createdAppointment);
    }
}