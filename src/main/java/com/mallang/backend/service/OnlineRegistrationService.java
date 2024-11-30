package com.mallang.backend.service;

import com.mallang.backend.domain.OnlineRegistration;
import com.mallang.backend.dto.OnlineRegistrationDTO;
import com.mallang.backend.repository.OnlineRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnlineRegistrationService {

    private final OnlineRegistrationRepository registrationRepository;

    // 새로운 접수 등록
    public OnlineRegistrationDTO registerOnline(OnlineRegistrationDTO registrationDTO) {
        OnlineRegistration registration = convertToEntity(registrationDTO);
        OnlineRegistration savedRegistration = registrationRepository.save(registration);
        return convertToDTO(savedRegistration);
    }

    // 모든 접수 조회
    public List<OnlineRegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(this::convertToDTO)
                .map(registration -> {
                    registration.setSymptom(null); // 전체 조회에서는 증상 제외
                    return registration;
                })
                .collect(Collectors.toList());
    }

    // 특정 접수 상세 조회
    public OnlineRegistrationDTO getRegistrationDetails(Long id) {
        OnlineRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 접수 정보를 찾을 수 없습니다."));
        return convertToDTO(registration); // 상세 조회 시 증상 포함
    }

    // 엔티티를 DTO로 변환
    private OnlineRegistrationDTO convertToDTO(OnlineRegistration registration) {
        return OnlineRegistrationDTO.builder()
                .id(registration.getId())
                .patientName(registration.getPatientName())
                .doctorName(registration.getDoctorName())
                .registrationDate(registration.getRegistrationDate())
                .registrationTime(registration.getRegistrationTime())
                .phoneNumber(registration.getPhoneNumber())
                .department(registration.getDepartment())
                .visitType(registration.getVisitType())
                .symptom(registration.getSymptom()) // 증상 포함
                .build();
    }

    // DTO를 엔티티로 변환
    private OnlineRegistration convertToEntity(OnlineRegistrationDTO registrationDTO) {
        return OnlineRegistration.builder()
                .id(registrationDTO.getId())
                .patientName(registrationDTO.getPatientName())
                .doctorName(registrationDTO.getDoctorName())
                .registrationDate(registrationDTO.getRegistrationDate())
                .registrationTime(registrationDTO.getRegistrationTime())
                .phoneNumber(registrationDTO.getPhoneNumber())
                .department(registrationDTO.getDepartment())
                .visitType(registrationDTO.getVisitType())
                .symptom(registrationDTO.getSymptom())
                .build();
    }
}