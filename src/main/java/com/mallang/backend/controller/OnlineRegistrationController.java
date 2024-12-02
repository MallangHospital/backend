package com.mallang.backend.controller;

import com.mallang.backend.config.CustomMemberDetails;
import com.mallang.backend.domain.Member;
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

    // 새로운 접수 등록
    @PostMapping
    public ResponseEntity<OnlineRegistrationDTO> registerOnline(
            @AuthenticationPrincipal CustomMemberDetails userDetails, // 인증된 사용자 정보
            @RequestBody OnlineRegistrationDTO registrationDTO) {

        // 인증된 사용자 정보에서 Member 객체 추출
        Member member = userDetails.getMember();

        // Service 호출 시 Member 객체 전달
        OnlineRegistrationDTO savedRegistration = registrationService.registerOnline(registrationDTO, member);
        return ResponseEntity.ok(savedRegistration);
    }

    // 모든 접수 조회
    @GetMapping
    public ResponseEntity<List<OnlineRegistrationDTO>> getAllRegistrations() {
        List<OnlineRegistrationDTO> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    // 특정 ID 접수 조회
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