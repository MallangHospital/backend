package com.mallang.backend.controller;
import com.mallang.backend.dto.OnlineRegistrationDTO;
import com.mallang.backend.service.OnlineRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OnlineRegistrationController {
    //온라인 등록
    private final OnlineRegistrationService onlineRegistrationService;

    // 전체 등록 조회
    @GetMapping
    public ResponseEntity<List<OnlineRegistrationDTO>> getAllRegistrations() {
        List<OnlineRegistrationDTO> registrations = onlineRegistrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    // 특정 등록 상세 보기
    @GetMapping("/{id}")
    public ResponseEntity<OnlineRegistrationDTO> getRegistrationById(@PathVariable Long id) {
        OnlineRegistrationDTO registration = onlineRegistrationService.getRegistrationById(id);
        return ResponseEntity.ok(registration);
    }
}