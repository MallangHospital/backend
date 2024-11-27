package com.mallang.backend.service;

import com.mallang.backend.domain.DoctorVacation;
import com.mallang.backend.dto.DoctorVacationDTO;
import com.mallang.backend.repository.DoctorVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorVacationService {

    private final DoctorVacationRepository vacationRepository;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 휴진 정보를 찾을 수 없습니다."));
    }

    // 휴진 정보 등록
    public void createVacation(DoctorVacationDTO vacationDTO) {
        DoctorVacation vacation = new DoctorVacation();
        vacation.setDoctorName(vacationDTO.getDoctorName());
        vacation.setStartDate(vacationDTO.getStartDate());
        vacation.setEndDate(vacationDTO.getEndDate());
        vacationRepository.save(vacation);
    }

    // 휴진 정보 수정
    public void updateVacation(Long id, DoctorVacationDTO vacationDTO) {
        DoctorVacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 휴진 정보를 찾을 수 없습니다."));

        vacation.setStartDate(vacationDTO.getStartDate());
        vacation.setEndDate(vacationDTO.getEndDate());
        vacationRepository.save(vacation);
    }

    // 휴진 정보 삭제
    public void deleteVacation(Long id) {
        vacationRepository.deleteById(id);
    }

    private DoctorVacationDTO convertToDTO(DoctorVacation vacation) {
        return DoctorVacationDTO.builder()
                .id(vacation.getId())
                .doctorName(vacation.getDoctorName())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .build();
    }
}