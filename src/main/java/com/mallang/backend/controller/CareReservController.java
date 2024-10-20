package com.mallang.backend.controller;

import com.mallang.backend.dto.CareReservDTO;
import com.mallang.backend.service.CareReservService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/care-reserv")
@RequiredArgsConstructor
public class CareReservController {
    private final CareReservService careReservService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody CareReservDTO careReservDTO) {
        careReservService.createReserv(careReservDTO);
        return ResponseEntity.ok("예약 성공");
    }
}