package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Vacation;
import com.mallang.backend.dto.VacationDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository;
    private final DoctorRepository doctorRepository;

    public VacationDTO createVacation(Long doctorId, VacationDTO vacationDTO) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Vacation vacation = Vacation.builder()
                .startDate(vacationDTO.getStartDate())
                .endDate(vacationDTO.getEndDate())
                .doctor(doctor)
                .build();

        Vacation savedVacation = vacationRepository.save(vacation);

        return VacationDTO.builder()
                .id(savedVacation.getId())
                .startDate(savedVacation.getStartDate())
                .endDate(savedVacation.getEndDate())
                .doctorId(savedVacation.getDoctor().getId())
                .doctorName(savedVacation.getDoctor().getName()) // 이름 포함
                .build();
    }

    public List<VacationDTO> getAllVacations() {
        return vacationRepository.findAll().stream()
                .map(vacation -> VacationDTO.builder()
                        .id(vacation.getId())
                        .startDate(vacation.getStartDate())
                        .endDate(vacation.getEndDate())
                        .doctorId(vacation.getDoctor().getId())
                        .doctorName(vacation.getDoctor().getName()) // 이름 포함
                        .build())
                .collect(Collectors.toList());
    }

    public List<VacationDTO> getVacationsByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        return doctor.getVacations().stream()
                .map(vacation -> VacationDTO.builder()
                        .id(vacation.getId())
                        .startDate(vacation.getStartDate())
                        .endDate(vacation.getEndDate())
                        .doctorId(doctor.getId())
                        .doctorName(doctor.getName()) // 이름 포함
                        .build())
                .collect(Collectors.toList());
    }

    public VacationDTO updateVacation(Long vacationId, VacationDTO vacationDTO) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new IllegalArgumentException("Vacation not found"));

        vacation.setStartDate(vacationDTO.getStartDate());
        vacation.setEndDate(vacationDTO.getEndDate());

        Vacation updatedVacation = vacationRepository.save(vacation);

        return VacationDTO.builder()
                .id(updatedVacation.getId())
                .startDate(updatedVacation.getStartDate())
                .endDate(updatedVacation.getEndDate())
                .doctorId(updatedVacation.getDoctor().getId())
                .doctorName(updatedVacation.getDoctor().getName()) // 이름 포함
                .build();
    }

    public void deleteVacation(Long vacationId) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new IllegalArgumentException("Vacation not found"));

        vacationRepository.delete(vacation);
    }

    public boolean isDoctorOnVacation(Long doctorId) {
        LocalDate today = LocalDate.now();
        return vacationRepository.isDoctorOnVacation(doctorId, today);
    }
}