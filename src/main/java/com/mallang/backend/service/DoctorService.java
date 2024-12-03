package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;
    private final S3UploaderService s3UploaderService; // S3 업로드 서비스 추가

    // 모든 의사 조회
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 의사 조회
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // 의사 생성
    public DoctorDTO createDoctor(DoctorDTO doctorDTO, MultipartFile photo) {
        Doctor doctor = convertToEntity(doctorDTO);

        // 소속 진료과 설정
        if (doctorDTO.getDepartmentId() != null) {
            departmentRepository.findById(doctorDTO.getDepartmentId())
                    .ifPresent(doctor::setDepartment);
        }

        if (photo != null && !photo.isEmpty()) {
            // S3에 사진 업로드
            doctor.setPhotoUrl(uploadPhotoToS3(photo));
        }

        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    public boolean updateDoctorById(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()) {
            return false;
        }

        Doctor doctor = optionalDoctor.get();

        // 전달받은 JSON 필드만 업데이트
        if (doctorDTO.getName() != null) {
            doctor.setName(doctorDTO.getName());
        }
        if (doctorDTO.getDepartmentId() != null) {
            departmentRepository.findById(doctorDTO.getDepartmentId())
                    .ifPresent(doctor::setDepartment);
        }
        if (doctorDTO.getPhoneNumber() != null) {
            doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        }
        if (doctorDTO.getSpecialty() != null) {
            doctor.setSpecialty(doctorDTO.getSpecialty());
        }
        if (doctorDTO.getPosition() != null) {
            doctor.setPosition(doctorDTO.getPosition());
        }

        // 파일 처리: 새로운 사진이 첨부된 경우 S3에 업로드
        if (photo != null && !photo.isEmpty()) {
            // 기존 S3 파일 삭제 후 새로운 파일 업로드
            if (doctor.getPhotoUrl() != null) {
                deletePhotoFromS3(doctor.getPhotoUrl());
            }
            doctor.setPhotoUrl(uploadPhotoToS3(photo));
        }

        doctorRepository.save(doctor);
        return true;
    }


    // 특정 부서의 의사 조회
    public List<DoctorDTO> getDoctorsByDepartmentId(Long departmentId) {
        List<Doctor> doctors = doctorRepository.findByDepartmentId(departmentId);
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    // 의사 삭제
    public boolean deleteDoctorById(Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            // S3에서 사진 삭제
            if (doctor.getPhotoUrl() != null) {
                deletePhotoFromS3(doctor.getPhotoUrl());
            }
            doctorRepository.delete(doctor);
            return true;
        }
        return false;
    }

    // S3에 사진 업로드
    private String uploadPhotoToS3(MultipartFile photo) {
        try {
            return s3UploaderService.upload(photo, "doctors");
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패: " + e.getMessage());
        }
    }

    // S3에서 사진 삭제
    private void deletePhotoFromS3(String photoUrl) {
        try {
            s3UploaderService.delete(photoUrl); // S3UploaderService에서 삭제 메서드 구현 필요
        } catch (Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패: " + e.getMessage());
        }
    }



    // 엔티티 -> DTO 변환
    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .photoUrl(doctor.getPhotoUrl()) // S3 URL 반환
                .position(doctor.getPosition())
                .phoneNumber(doctor.getPhoneNumber())
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .build();
    }

    // DTO -> 엔티티 변환
    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .name(doctorDTO.getName())
                .specialty(doctorDTO.getSpecialty())
                .position(doctorDTO.getPosition())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .build();
    }
}




