package com.mallang.backend.service;

import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.OnlineRegistration;
import com.mallang.backend.dto.OnlineRegistrationDTO;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.OnlineRegistrationRepository;
import com.mallang.backend.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnlineRegistrationService {

    private final OnlineRegistrationRepository onlineRegistrationRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public OnlineRegistrationDTO registerOnline(OnlineRegistrationDTO registrationDTO, Member member) {
        // 로그인한 사용자 정보로 환자 이름 및 전화번호 설정
        registrationDTO.setPatientName(member.getName());
        registrationDTO.setPhoneNumber(member.getPhoneNum());

        // doctorId로 doctorName 자동 설정
        if (registrationDTO.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(registrationDTO.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 의사 ID입니다."));
            registrationDTO.setDoctorName(doctor.getName()); // doctorName 자동 설정
        }

        // DTO를 엔티티로 변환 후 저장
        OnlineRegistration registration = convertToEntity(registrationDTO);
        OnlineRegistration savedRegistration = onlineRegistrationRepository.save(registration);

        // 저장된 데이터를 DTO로 변환 후 반환
        return convertToDTO(savedRegistration);
    }

    public OnlineRegistrationDTO getRegistrationDetails(Long id) {
        OnlineRegistration registration = onlineRegistrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록 정보를 찾을 수 없습니다."));
        return convertToDTO(registration);
    }

    public List<OnlineRegistrationDTO> getAllRegistrations() {
        return onlineRegistrationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public int getRegistrationCountByDoctor(Long doctorId) {
        // 특정 의사(doctorId)에 대한 접수 건수 조회
        return (int) onlineRegistrationRepository.countByDoctorId(doctorId);

    }



    private OnlineRegistration convertToEntity(OnlineRegistrationDTO registrationDTO) {
        OnlineRegistration registration = new OnlineRegistration();
        registration.setPatientName(registrationDTO.getPatientName());
        registration.setDoctorName(registrationDTO.getDoctorName());
        registration.setPhoneNumber(registrationDTO.getPhoneNumber());
        registration.setVisitType(registrationDTO.getVisitType());
        registration.setSymptom(registrationDTO.getSymptom());
        registration.setRegistrationTime(registrationDTO.getRegistrationTime());
        registration.setRegistrationDate(registrationDTO.getRegistrationDate());

        if (registrationDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(registrationDTO.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 부서 ID입니다."));
            registration.setDepartmentId(department);
        }

        if (registrationDTO.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(registrationDTO.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 의사 ID입니다."));
            registration.setDoctorId(doctor);
        }

        return registration;
    }

    private OnlineRegistrationDTO convertToDTO(OnlineRegistration registration) {
        return OnlineRegistrationDTO.builder()
                .id(registration.getId())
                .patientName(registration.getPatientName())
                .doctorName(registration.getDoctorName())
                .phoneNumber(registration.getPhoneNumber())
                .visitType(registration.getVisitType())
                .symptom(registration.getSymptom())
                .registrationTime(registration.getRegistrationTime())
                .departmentId(registration.getDepartmentId() != null ? registration.getDepartmentId().getId() : null)
                .department(registration.getDepartmentId() != null ? registration.getDepartmentId().getName() : null)
                .doctorId(registration.getDoctorId() != null ? registration.getDoctorId().getId() : null)
                .registrationDate(registration.getRegistrationDate())
                .build();
    }
}