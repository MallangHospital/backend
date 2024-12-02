package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<?> createDoctor(
            @RequestPart("doctor") String doctorJson,
            @RequestPart(value = "photo", required = true) MultipartFile photo) {
        try {
            // JSON 데이터를 DoctorDTO로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            DoctorDTO doctorDTO = objectMapper.readValue(doctorJson, DoctorDTO.class);

            // 디버깅 로그
            System.out.println("Doctor Name: " + doctorDTO.getName());
            if (photo != null) {
                System.out.println("Photo File Name: " + photo.getOriginalFilename());
            }

            // 여기에서 저장 로직을 호출 (예: doctorService.saveDoctor(doctorDTO))
            doctorService.createDoctor(doctorDTO, photo);
            return ResponseEntity.ok("Doctor created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid JSON format");
        }
    }

    // 의사 전체 조회
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // 특정 ID로 의사 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            DoctorDTO doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 의사 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        if (doctorService.deleteDoctor(id)) {
            return ResponseEntity.ok("Doctor deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Doctor not found.");
        }
    }

    // 의사 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestPart("doctor") DoctorDTO doctorDTO,
            @RequestPart(value = "photo", required = true) MultipartFile photo) {
        try {
            boolean updatedDoctor = doctorService.updateDoctor(id, doctorDTO, photo);
            return ResponseEntity.ok(updatedDoctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 특정 부서의 의사 조회
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        List<DoctorDTO> doctors = doctorService.getDoctorsByDepartmentId(departmentId);
        return ResponseEntity.ok(doctors);
    }
}