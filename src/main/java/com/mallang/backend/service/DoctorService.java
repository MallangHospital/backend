package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Department;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    // 전체 의사 조회
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 특정 부서의 의사 조회
    public List<DoctorDTO> getDoctorsByDepartmentId(Long departmentId) {
        List<Doctor> doctors = doctorRepository.findByDepartmentId(departmentId);
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 의사 정보 등록
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);

        // 소속 진료과 설정
        if (doctorDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(doctorDTO.getDepartmentId());
            department.ifPresent(doctor::setDepartment);

            // departmentId에 따른 specialty 설정
            doctor.setSpecialty(getSpecialtyByDepartmentId(doctorDTO.getDepartmentId()));
        }

        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    // 의사 정보 수정
    public boolean updateDoctor(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            return false;
        }

        Doctor doctor = doctorOptional.get();
        doctor.setName(doctorDTO.getName());
        doctor.setContact(doctorDTO.getContact());
        doctor.setPhotoUrl(doctorDTO.getPhotoUrl());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setPhotoPath(doctorDTO.getPhotoPath());
        doctor.setAdminId(doctorDTO.getAdminId());

        // 소속 진료과 업데이트 및 specialty 설정
        if (doctorDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(doctorDTO.getDepartmentId());
            department.ifPresent(doctor::setDepartment);
            doctor.setSpecialty(getSpecialtyByDepartmentId(doctorDTO.getDepartmentId()));
        }

        doctorRepository.save(doctor);
        return true;
    }

    // 의사 정보 삭제
    public boolean deleteDoctor(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            return false;
        }
        doctorRepository.deleteById(id);
        return true;
    }
    // 특정 ID로 의사 조회
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with ID " + id + " not found."));
        return convertToDTO(doctor);
    }

    // departmentId에 따른 specialty 설정
    private String getSpecialtyByDepartmentId(Long departmentId) {
        switch (departmentId.intValue()) {
            case 1:
                return "내과";
            case 2:
                return "산부인과";
            case 3:
                return "소아청소년과";
            case 4:
                return "외과";
            default:
                return "기타";
        }
    }

    // 엔티티를 DTO로 변환
    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .contact(doctor.getContact())
                .photoUrl(doctor.getPhotoUrl())
                .position(doctor.getPosition())
                .phoneNumber(doctor.getPhoneNumber())
                .photoPath(doctor.getPhotoPath())
                .adminId(doctor.getAdminId())
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .build();
    }

    // DTO를 엔티티로 변환
    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .name(doctorDTO.getName())
                .contact(doctorDTO.getContact())
                .photoUrl(doctorDTO.getPhotoUrl())
                .position(doctorDTO.getPosition())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .photoPath(doctorDTO.getPhotoPath())
                .adminId(doctorDTO.getAdminId())
                .build();
    }
}