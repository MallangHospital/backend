package com.mallang.backend.controller;

import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.dto.DoctorVacationDTO;
import com.mallang.backend.service.DoctorService;
import com.mallang.backend.service.DoctorVacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 관리자 전용
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorVacationService doctorVacationService;

    // 모든 의료진 정보 조회
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // 특정 의료진 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // 의료진 정보 등록
    @PostMapping
    public ResponseEntity<?> createDoctor(
            @RequestPart DoctorDTO doctorDTO,
            @RequestPart(required = false) MultipartFile photo
    ) {
        doctorService.createDoctor(doctorDTO, photo);
        return ResponseEntity.ok("의료진 정보가 성공적으로 등록되었습니다.");
    }

    // 의료진 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestPart DoctorDTO doctorDTO,
            @RequestPart(required = false) MultipartFile photo
    ) {
        doctorService.updateDoctor(id, doctorDTO, photo);
        return ResponseEntity.ok("의료진 정보가 성공적으로 수정되었습니다.");
    }

    // 의료진 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("의료진 정보가 성공적으로 삭제되었습니다.");
    }

    // 모든 휴진 정보 조회
    @GetMapping("/vacations")
    public ResponseEntity<List<DoctorVacationDTO>> getAllDoctorVacations() {
        return ResponseEntity.ok(doctorVacationService.getAllVacations());
    }

    // 특정 휴진 정보 조회
    @GetMapping("/vacations/{id}")
    public ResponseEntity<DoctorVacationDTO> getDoctorVacationById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorVacationService.getVacationById(id));
    }

    // 휴진 정보 등록
    @PostMapping("/vacations")
    public ResponseEntity<?> createDoctorVacation(@RequestBody DoctorVacationDTO vacationDTO) {
        doctorVacationService.createVacation(vacationDTO);
        return ResponseEntity.ok("휴진 정보가 성공적으로 등록되었습니다.");
    }

    // 휴진 정보 수정
    @PutMapping("/vacations/{id}")
    public ResponseEntity<?> updateDoctorVacation(
            @PathVariable Long id,
            @RequestBody DoctorVacationDTO vacationDTO
    ) {
        doctorVacationService.updateVacation(id, vacationDTO);
        return ResponseEntity.ok("휴진 정보가 성공적으로 수정되었습니다.");
    }

    // 휴진 정보 삭제
    @DeleteMapping("/vacations/{id}")
    public ResponseEntity<?> deleteDoctorVacation(@PathVariable Long id) {
        doctorVacationService.deleteVacation(id);
        return ResponseEntity.ok("휴진 정보가 성공적으로 삭제되었습니다.");
    }
}