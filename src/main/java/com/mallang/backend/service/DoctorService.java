package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class DoctorService {
    private final DoctorRepository doctorRepository;

    // 모든 의사 목록을 가져오는 메서드
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Doctor 객체를 DoctorDTO로 변환하는 메서드
    private DoctorDTO convertToDTO(Doctor doctor) {
        // Department가 null일 경우 처리
        String departmentName = (doctor.getDepartment() != null) ? doctor.getDepartment().getName() : null;

        // LocalDate -> String 변환 (null 처리)
        String vacationStartDate = (doctor.getVacationStartDate() != null) ? doctor.getVacationStartDate().toString() : null;
        String vacationEndDate = (doctor.getVacationEndDate() != null) ? doctor.getVacationEndDate().toString() : null;

        return new DoctorDTO(
                doctor.getName(),
                doctor.getPosition(),
                doctor.getPhoneNumber(),
                doctor.getPhotoUrl(),
                vacationStartDate,
                vacationEndDate,
                doctor.getHistory(),  // 여기서 강제 변환 없이 바로 사용
                departmentName
        );
    }
}