package com.mallang.backend.controller;

import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.service.DoctorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}