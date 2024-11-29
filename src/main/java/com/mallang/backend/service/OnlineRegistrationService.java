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

    private final OnlineRegistrationRepository onlineRegistrationRepository;

    // 전체 등록 조회
    public List<OnlineRegistrationDTO> getAllRegistrations() {
        return onlineRegistrationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 등록 상세 보기
    public OnlineRegistrationDTO getRegistrationById(Long id) {
        return onlineRegistrationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("OnlineRegistration not found with id: " + id));
    }

    // Entity -> DTO 변환
    private OnlineRegistrationDTO convertToDTO(OnlineRegistration registration) {
        return OnlineRegistrationDTO.builder()
                .id(registration.getId())
                .patientName(registration.getPatientName())
                .registrationDateTime(registration.getRegistrationDateTime())
                .doctorName(registration.getDoctor().getName())
                .details(registration.getDetails())
                .build();
    }
}