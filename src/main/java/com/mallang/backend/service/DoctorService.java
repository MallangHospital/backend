package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // 모든 의료진 정보 조회
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 의료진 정보 조회
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
    }

    // 의료진 등록
    public void createDoctor(DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = convertToEntity(doctorDTO);

        // 사진 처리 로직 (예: 파일 저장)
        if (photo != null && !photo.isEmpty()) {
            // 사진 저장 로직을 구현
            doctor.setPhotoPath("uploaded/photo/path"); // 예시 경로
        }

        doctorRepository.save(doctor);
    }

    // 의료진 수정
    public void updateDoctor(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));

        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContact(doctorDTO.getContact());

        if (photo != null && !photo.isEmpty()) {
            // 사진 저장 로직을 구현
            doctor.setPhotoPath("updated/photo/path"); // 예시 경로
        }

        doctorRepository.save(doctor);
    }

    // 의료진 삭제
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    // DTO -> Entity 변환
    private Doctor convertToEntity(DoctorDTO dto) {
        return Doctor.builder()
                .name(dto.getName())
                .specialty(dto.getSpecialty())
                .contact(dto.getContact())
                .photoPath(dto.getPhotoPath())
                .build();
    }

    // Entity -> DTO 변환
    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .contact(doctor.getContact())
                .photoPath(doctor.getPhotoPath())
                .build();
    }
}