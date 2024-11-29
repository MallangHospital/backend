package com.mallang.backend.controller;

import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // 모든 의료진 정보 조회
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }



    @PostMapping
    public ResponseEntity<String> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.createDoctor(doctorDTO, null); // 사진 없이 등록
        return ResponseEntity.ok("Doctor created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctor(
            @PathVariable Long id,
            @RequestPart("doctor") DoctorDTO doctorDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photo // 사진은 선택 사항
    ) {
        doctorService.updateDoctor(id, doctorDTO, photo);
        return ResponseEntity.ok("Doctor updated successfully.");
    }

    // 의료진 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}