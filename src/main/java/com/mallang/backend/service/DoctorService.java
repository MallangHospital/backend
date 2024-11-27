package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final Path uploadDir = Paths.get("uploads");

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
                .orElseThrow(() -> new IllegalArgumentException("해당 의료진을 찾을 수 없습니다."));
    }

    // 의료진 정보 등록
    public void createDoctor(DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContact(doctorDTO.getContact());

        // 사진 저장
        if (photo != null && !photo.isEmpty()) {
            doctor.setPhotoPath(savePhoto(photo));
        }

        doctorRepository.save(doctor);
    }

    // 의료진 정보 수정
    public void updateDoctor(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 의료진을 찾을 수 없습니다."));

        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContact(doctorDTO.getContact());

        // 사진 저장
        if (photo != null && !photo.isEmpty()) {
            doctor.setPhotoPath(savePhoto(photo));
        }

        doctorRepository.save(doctor);
    }

    // 의료진 정보 삭제
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private String savePhoto(MultipartFile photo) {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path targetLocation = uploadDir.resolve(uniqueFileName);
            Files.copy(photo.getInputStream(), targetLocation);

            return targetLocation.toString();
        } catch (IOException e) {
            throw new RuntimeException("사진 업로드 실패: " + e.getMessage(), e);
        }
    }

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