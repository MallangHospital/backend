package com.mallang.backend.service;

import com.mallang.backend.domain.HealthcareReserve;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.repository.HealthcareReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthcareReserveService {
    private final HealthcareReserveRepository healthcareReserveRepository;

    @Transactional
    public HealthcareReserveDTO createReservation(HealthcareReserveDTO dto) {
        HealthcareReserve reservation = HealthcareReserve.builder()
                .name(dto.getName())
                .memberId(dto.getMemberId())
                .phoneNumber(dto.getPhoneNumber())
                .reserveDate(dto.getReserveDate())
                .hType(dto.getHType())
                .build();
        HealthcareReserve savedReservation = healthcareReserveRepository.save(reservation);
        return HealthcareReserveDTO.builder()
                .hId(savedReservation.getHId())
                .name(savedReservation.getName())
                .memberId(savedReservation.getMemberId())
                .phoneNumber(savedReservation.getPhoneNumber())
                .reserveDate(savedReservation.getReserveDate())
                .hType(savedReservation.getHType())
                .build();
    }


}