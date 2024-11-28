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

    // 건강검진 예약 생성
    @Transactional
    public HealthcareReserveDTO createHealthcareReserve(HealthcareReserveDTO dto) {
        HealthcareReserve reservation = HealthcareReserve.builder()
                .name(dto.getName())
                .memberId(dto.getMemberId())
                .phoneNumber(dto.getPhoneNumber())
                .reserveDate(dto.getReserveDate())
                .hType(dto.getHType())
                .build();

        HealthcareReserve savedReservation = healthcareReserveRepository.save(reservation);

        return convertToDTO(savedReservation);
    }

    // 특정 회원의 건강검진 예약 조회
    @Transactional(readOnly = true)
    public List<HealthcareReserveDTO> getHealthReservesByMemberId(String memberId) {
        List<HealthcareReserve> reserves = healthcareReserveRepository.findByMemberId(memberId);

        return reserves.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 건강검진 예약 조회
    @Transactional(readOnly = true)
    public HealthcareReserveDTO getHealthcareReserveById(Long id) {
        HealthcareReserve reserve = healthcareReserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Healthcare reserve not found with id: " + id));

        return convertToDTO(reserve);
    }

    // 모든 건강검진 예약 조회 (관리자 전용)
    @Transactional(readOnly = true)
    public List<HealthcareReserveDTO> getAllHealthReserves() {
        return healthcareReserveRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 건강검진 예약 취소
    @Transactional
    public void cancelHealthCheck(Long id) {
        HealthcareReserve reserve = healthcareReserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Healthcare reservation not found."));
        healthcareReserveRepository.delete(reserve);
    }

    // HealthcareReserve -> HealthcareReserveDTO 변환
    private HealthcareReserveDTO convertToDTO(HealthcareReserve reserve) {
        return HealthcareReserveDTO.builder()
                .hId(reserve.getHId())
                .name(reserve.getName())
                .memberId(reserve.getMemberId())
                .phoneNumber(reserve.getPhoneNumber())
                .reserveDate(reserve.getReserveDate())
                .hType(reserve.getHType())
                .build();
    }
}