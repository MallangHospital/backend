package com.mallang.backend.service;

import com.mallang.backend.domain.AvailableTime;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Schedule;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.AvailableTimeRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.ScheduleRepository;
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
    private final ScheduleRepository scheduleRepository;
    private final AvailableTimeRepository availableTimeRepository;


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

        // 파일 처리: 새로운 사진이 첨부된 경우 기존 사진을 삭제 후 저장
        if (photo != null && !photo.isEmpty()) {
            doctor.setPhotoUrl(saveFile(photo, doctor.getPhotoUrl()));
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

    // 의사 삭제
    public boolean deleteDoctorById(Long id) {
        if (doctorRepository.existsById(id)) {
            // 1. 의사의 스케줄 목록 가져오기
            List<Schedule> schedules = scheduleRepository.findByDoctorId(id);

            // 2. 각 스케줄에 연관된 AvailableTime 삭제
            for (Schedule schedule : schedules) {
                List<AvailableTime> availableTimes = availableTimeRepository.findByScheduleId(schedule.getId());
                availableTimeRepository.deleteAll(availableTimes); // AvailableTime 삭제
            }

            // 3. 스케줄 삭제
            scheduleRepository.deleteAll(schedules);

            // 4. 의사 삭제
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




