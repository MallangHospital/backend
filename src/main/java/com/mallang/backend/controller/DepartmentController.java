package com.mallang.backend.controller;

import com.mallang.backend.dto.DepartmentDTO;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    // 부서별 의사 조회
    @GetMapping("/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartmentId(@PathVariable Long departmentId) {
        List<DoctorDTO> doctors = departmentService.getDoctorsByDepartmentId(departmentId);
        return ResponseEntity.ok(doctors);
    }
}