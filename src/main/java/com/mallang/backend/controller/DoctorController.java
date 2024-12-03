package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        if (doctorDTO != null) {
            return ResponseEntity.ok(doctorDTO);
        } else {
            return ResponseEntity.badRequest().body("해당 의사를 찾을 수 없습니다.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(
            @RequestPart("doctorDTO") String doctorDTOString,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            DoctorDTO doctorDTO = objectMapper.readValue(doctorDTOString, DoctorDTO.class);
            DoctorDTO savedDoctor = doctorService.createDoctor(doctorDTO, photo);
            return ResponseEntity.ok(savedDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestPart("doctorDTO") String doctorDTOString,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            // JSON 데이터를 DoctorDTO로 변환
            DoctorDTO doctorDTO = objectMapper.readValue(doctorDTOString, DoctorDTO.class);

            // 서비스 호출
            boolean isUpdated = doctorService.updateDoctorById(id, doctorDTO, photo);
            if (isUpdated) {
                return ResponseEntity.ok("수정이 완료되었습니다!");
            } else {
                return ResponseEntity.badRequest().body("해당 의사를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        boolean isDeleted = doctorService.deleteDoctorById(id);
        if (isDeleted) {
            return ResponseEntity.ok("의사가 성공적으로 삭제되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 의사를 찾을 수 없습니다.");
        }
    }

    // 특정 부서의 의사 조회
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        List<DoctorDTO> doctors = doctorService.getDoctorsByDepartmentId(departmentId);
        return ResponseEntity.ok(doctors);
    }
}