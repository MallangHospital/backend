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

    public void updateDoctor(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));

        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContact(doctorDTO.getContact());

        // 사진 처리 로직 추가
        if (photo != null && !photo.isEmpty()) {
            // 파일 저장 로직 (예: 로컬 저장소나 S3 업로드)
            String photoPath = savePhoto(photo);
            doctor.setPhotoPath(photoPath);
        }

        doctorRepository.save(doctor);
    }

    // 사진 저장 로직 예제
    String savePhoto(MultipartFile photo) {
        try {
            String uploadDir = "uploads/photos/"; // 사진 저장 경로
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            // 디렉토리 생성 (필요 시)
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, photo.getBytes());

            return filePath.toString(); // 저장된 경로 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to save photo", e);
        }
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