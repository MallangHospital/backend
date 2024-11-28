package com.mallang.backend.service;
import com.mallang.backend.dto.DepartmentDTO;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getName()))
                .collect(Collectors.toList());
    }

    // 부서별 의사 조회
    public List<DoctorDTO> getDoctorsByDepartmentId(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId) // 메서드 이름 수정
                .stream()
                .map(doctor -> DoctorDTO.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .specialty(doctor.getSpecialty())
                        .contact(doctor.getContact())
                        .photoPath(doctor.getPhotoPath())
                        .build())
                .collect(Collectors.toList());
    }
}