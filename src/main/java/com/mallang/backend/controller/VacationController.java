package com.mallang.backend.controller;

import com.mallang.backend.dto.VacationDTO;
import com.mallang.backend.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @PostMapping
    public ResponseEntity<VacationDTO> createVacation(@RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.createVacation(vacationDTO.getDoctorId(), vacationDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAllVacations() {
        return ResponseEntity.ok(vacationService.getAllVacations());
    }

    @GetMapping("/{doctor_id}")
    public ResponseEntity<?> getVacationsByDoctorId(@PathVariable Long doctor_id) {
        return ResponseEntity.ok(vacationService.getVacationsByDoctorId(doctor_id));
    }

    @PutMapping("/{vacationId}")
    public ResponseEntity<VacationDTO> updateVacation(
            @PathVariable Long vacationId,
            @RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.updateVacation(vacationId, vacationDTO));
    }

    @DeleteMapping("/{vacationId}")
    public ResponseEntity<Void> deleteVacation(@PathVariable Long vacationId) {
        vacationService.deleteVacation(vacationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{doctorId}/status")
    public ResponseEntity<?> getVacationStatus(@PathVariable Long doctorId) {
        boolean onVacation = vacationService.isDoctorOnVacation(doctorId);
        return ResponseEntity.ok().body(Map.of("onVacation", onVacation));
    }
}