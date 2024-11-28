package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.DoctorVacation;
import com.mallang.backend.dto.DoctorVacationDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.DoctorVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
DoctorVacationService {

    private final DoctorVacationRepository vacationRepository;
    private final DoctorRepository doctorRepository; // DoctorRepository 추가

    // 모든 휴진 정보 조회
    public List<DoctorVacationDTO> getAllVacations() {
        return vacationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 휴진 정보 조회
    public DoctorVacationDTO getVacationById(Long id) {
        return vacationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Vacation not found with id: " + id));
    }

    // 휴진 정보 등록
    public void createVacation(DoctorVacationDTO dto) {
        DoctorVacation vacation = convertToEntity(dto);
        vacationRepository.save(vacation);
    }

    // 휴진 정보 수정
    public void updateVacation(Long id, DoctorVacationDTO dto) {
        DoctorVacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vacation not found with id: " + id));

        vacation.setStartDate(dto.getStartDate());
        vacation.setEndDate(dto.getEndDate());
        vacationRepository.save(vacation);
    }

    // 휴진 정보 삭제
    public void deleteVacation(Long id) {
        vacationRepository.deleteById(id);
    }

    // DTO -> 엔티티 변환
    private DoctorVacation convertToEntity(DoctorVacationDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + dto.getDoctorId()));

        return DoctorVacation.builder()
                .doctor(doctor)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    // 엔티티 -> DTO 변환
    private DoctorVacationDTO convertToDTO(DoctorVacation vacation) {
        return DoctorVacationDTO.builder()
                .id(vacation.getId())
                .doctorId(vacation.getDoctor().getId())
                .doctorName(vacation.getDoctor().getName())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .build();
    }
}