package com.mallang.backend.controller;

import com.mallang.backend.dto.VacationDTO;
import com.mallang.backend.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @PostMapping("/{doctorId}")
    public ResponseEntity<VacationDTO> createVacation(
            @PathVariable Long doctorId,
            @RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.createVacation(doctorId, vacationDTO));
    }
}