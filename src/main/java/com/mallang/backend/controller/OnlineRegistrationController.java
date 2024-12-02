package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.dto.OnlineRegistrationDTO;
import com.mallang.backend.service.OnlineRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class OnlineRegistrationController {

    private final OnlineRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<OnlineRegistrationDTO> registerOnline(
            @AuthenticationPrincipal CustomMemberDetails userDetails,
            @RequestBody OnlineRegistrationDTO registrationDTO) {

        OnlineRegistrationDTO savedRegistration = registrationService.registerOnline(registrationDTO, userDetails.getMember());
        return ResponseEntity.ok(savedRegistration);
    }

    @GetMapping
    public ResponseEntity<List<OnlineRegistrationDTO>> getAllRegistrations() {
        List<OnlineRegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }
    @GetMapping("/doctor/{doctorId}/count")
    public ResponseEntity<Integer> getRegistrationCountByDoctor(@PathVariable Long doctorId) {
        int count = registrationService.getRegistrationCountByDoctor(doctorId);
        return ResponseEntity.ok(count);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistrationDetails(@PathVariable Long id) {
        try {
            OnlineRegistrationDTO registration = registrationService.getRegistrationDetails(id);
            return ResponseEntity.ok(registration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}