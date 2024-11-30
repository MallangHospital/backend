package com.mallang.backend.service;

import com.mallang.backend.domain.HealthcareReserve;
import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.repository.HealthcareReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .status("예약")
                .hType(dto.getHType())
                .build();
        HealthcareReserve savedReservation = healthcareReserveRepository.save(reservation);
        return HealthcareReserveDTO.builder()
                .hId(savedReservation.getHId())
                .name(savedReservation.getName())
                .memberId(savedReservation.getMemberId())
                .phoneNumber(savedReservation.getPhoneNumber())
                .reserveDate(savedReservation.getReserveDate())
                .status("예약")
                .hType(savedReservation.getHType())
                .build();
    }

    // 특정 회원의 건강검진 예약 조회
    @Transactional(readOnly = true)
    public List<HealthcareReserveDTO> getHealthReservesByMemberId(String memberId) {
        List<HealthcareReserve> reserves = healthcareReserveRepository.findByMemberId(memberId);
        return reserves.stream()
                .map(reserve -> HealthcareReserveDTO.builder()
                        .hId(reserve.getHId())
                        .name(reserve.getName())
                        .memberId(reserve.getMemberId())
                        .phoneNumber(reserve.getPhoneNumber())
                        .reserveDate(reserve.getReserveDate())
                        .hType(reserve.getHType())
                        .status(reserve.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 모든 건강검진 예약 조회
    @Transactional(readOnly = true)
    public List<HealthcareReserveDTO> getAllHealthReserves() {
        List<HealthcareReserve> healthReserves = healthcareReserveRepository.findAll();
        return healthReserves.stream()
                .map(reserve -> HealthcareReserveDTO.builder()
                        .hId(reserve.getHId())
                        .name(reserve.getName())
                        .memberId(reserve.getMemberId())
                        .phoneNumber(reserve.getPhoneNumber())
                        .reserveDate(reserve.getReserveDate())
                        .hType(reserve.getHType())
                        .status(reserve.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 건강검진 예약 취소
    @Transactional
    public void cancelHealthCheck(Long id) {
        HealthcareReserve reserve = healthcareReserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Healthcare reservation not found."));

        // 상태를 "취소"로 변경
        reserve.setStatus("취소");
        healthcareReserveRepository.save(reserve);
    }

}