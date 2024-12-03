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
            // 기존 파일 삭제 및 새 파일 저장
            doctor.setPhotoUrl(saveFile(photo, doctor.getPhotoUrl()));
        }

        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    // 의사 수정
    public boolean updateDoctorById(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()) {
            return false;
        }

        Doctor doctor = optionalDoctor.get();

        // 기본 정보 업데이트
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setAdminId(doctorDTO.getAdminId());

        // 소속 진료과 설정
        if (doctorDTO.getDepartmentId() != null) {
            departmentRepository.findById(doctorDTO.getDepartmentId())
                    .ifPresent(doctor::setDepartment);
        }

        // 파일 업데이트 처리 (새 파일이 기존 파일과 다를 경우만)
        if (photo != null && !photo.isEmpty()) {
            if (!isSameFile(photo, doctor.getPhotoUrl())) {
                doctor.setPhotoUrl(saveFile(photo, doctor.getPhotoUrl())); // 파일이 다르면 저장
            }
        }

        doctorRepository.save(doctor);
        return true;
    }

    // 기존 파일과 새 파일이 동일한지 확인하는 메서드
    private boolean isSameFile(MultipartFile newFile, String existingFileUrl) {
        if (existingFileUrl == null || newFile == null || newFile.isEmpty()) {
            return false; // 기존 파일이 없거나 새 파일이 없으면 같지 않음
        }

        try {
            // 기존 파일 경로 설정
            String uploadDir = "src/main/resources/static/uploads/doctors/";
            Path existingFilePath = Paths.get(uploadDir, existingFileUrl.replace("/uploads/doctors/", ""));

            // 파일 존재 여부 확인
            if (!Files.exists(existingFilePath)) {
                return false; // 기존 파일이 없으면 같지 않음
            }

            // 기존 파일과 새 파일의 내용 비교
            byte[] existingFileBytes = Files.readAllBytes(existingFilePath);
            byte[] newFileBytes = newFile.getBytes();

            return java.util.Arrays.equals(existingFileBytes, newFileBytes); // 내용 비교
        } catch (IOException e) {
            throw new RuntimeException("파일 비교 중 오류 발생: " + e.getMessage(), e);
        }
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
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String saveFile(MultipartFile file, String existingFileUrl) {
        try {
            // 기존 파일 삭제
            if (existingFileUrl != null) {
                deleteFile(existingFileUrl);
            }

            // 파일 저장 경로 설정
            String uploadDir = "src/main/resources/static/uploads/doctors/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 디렉토리 생성
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // 저장된 파일의 URL 반환
            return "/uploads/doctors/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }
    }

    // 기존 파일 삭제 메서드
    private void deleteFile(String photoUrl) {
        try {
            String uploadDir = "src/main/resources/static/uploads/doctors/";
            Path filePath = Paths.get(uploadDir, photoUrl.replace("/uploads/doctors/", ""));
            Files.deleteIfExists(filePath); // 파일이 존재할 경우 삭제
        } catch (IOException e) {
            throw new RuntimeException("기존 파일 삭제 실패: " + e.getMessage(), e);
        }
    }

    // 엔티티 -> DTO 변환
    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .photoUrl(doctor.getPhotoUrl())
                .position(doctor.getPosition())
                .phoneNumber(doctor.getPhoneNumber())
                .adminId(doctor.getAdminId())
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
                .adminId(doctorDTO.getAdminId())
                .build();
    }
}




