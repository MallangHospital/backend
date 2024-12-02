package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Department;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    // 의사 등록
    public DoctorDTO createDoctor(DoctorDTO doctorDTO, MultipartFile photo) throws IOException {
        System.out.println("Starting createDoctor with DTO: " + doctorDTO);

        Doctor doctor = convertToEntity(doctorDTO);

        // 소속 진료과 설정
        if (doctorDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(doctorDTO.getDepartmentId());
            if (department.isPresent()) {
                doctor.setDepartment(department.get());
                doctor.setSpecialty(getSpecialtyByDepartmentId(doctorDTO.getDepartmentId()));
            } else {
                System.out.println("Department not found for ID: " + doctorDTO.getDepartmentId());
            }
        } else {
            System.out.println("DepartmentId is null");
        }

        // 사진 파일 저장
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo);
            doctor.setPhotoPath(photoPath);
            doctor.setPhotoUrl("/uploads/doctors/" + photo.getOriginalFilename()); // URL 경로
            System.out.println("Photo saved at: " + photoPath);
        } else {
            System.out.println("No photo uploaded");
        }

        // Doctor 저장
        Doctor savedDoctor;
        try {
            savedDoctor = doctorRepository.save(doctor);
            System.out.println("Doctor saved successfully with ID: " + savedDoctor.getId());
        } catch (Exception e) {
            System.out.println("Error saving doctor: " + e.getMessage());
            throw new RuntimeException("Doctor saving failed", e);
        }

        return convertToDTO(savedDoctor);
    }

    // 의사 정보 수정
    public boolean updateDoctor(Long id, DoctorDTO doctorDTO, MultipartFile photo) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor not found.");
        }

        Doctor doctor = doctorOptional.get();
        doctor.setName(doctorDTO.getName());
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

        // 사진 파일 저장
        if (photo != null && !photo.isEmpty()) {
            String photoPath = saveFile(photo);
            doctor.setPhotoPath(photoPath);
        }

        doctorRepository.save(doctor);
        return true;
    }

    // 의사 삭제
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

    // 파일 저장 로직
    private String saveFile(MultipartFile file) {
        try {
            // 현재 애플리케이션의 실행 경로를 기준으로 디렉터리 설정
            String baseDir = System.getProperty("user.dir"); // 애플리케이션 실행 디렉터리
            String uploadDir = baseDir + File.separator + "uploads" + File.separator + "doctors";

            // 디렉터리 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean dirsCreated = dir.mkdirs(); // 디렉터리 생성
                if (!dirsCreated) {
                    throw new IOException("Failed to create directories: " + uploadDir);
                }
            }

            // 파일 이름 생성
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            File uploadFile = new File(dir, fileName);

            // 파일 저장
            file.transferTo(uploadFile);
            System.out.println("File saved at: " + uploadFile.getAbsolutePath());

            return uploadFile.getAbsolutePath(); // 저장된 파일 경로 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
        }
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
                .position(doctor.getPosition())
                .phoneNumber(doctor.getPhoneNumber())
                .adminId(doctor.getAdminId())
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .build();
    }

    // DTO를 엔티티로 변환
    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .name(doctorDTO.getName())
                .position(doctorDTO.getPosition())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .adminId(doctorDTO.getAdminId())
                .build();
    }
}