package com.mallang.backend.service;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.dto.PatientDTO;
import com.mallang.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentDetailsService {

    private final AppointmentRepository appointmentRepository;

    // 모든 예약 정보 조회
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 ID로 상세 정보 조회
    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));
    }

    // Appointment 엔티티를 AppointmentDTO로 변환
    private AppointmentDTO convertToDTO(Appointment appointment) {
        PatientDTO patientDTO = PatientDTO.builder()
                .id(Long.valueOf(appointment.getMember().getMid())) // 환자 ID
                .name(appointment.getMember().getName()) // 환자 이름
                .contact(appointment.getMember().getPhoneNum()) // 연락처
                .symptomDescription(appointment.getSymptomDescription()) // 증상
                .build();

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientName(patientDTO.getName())
                .doctorName(appointment.getDoctor().getName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .symptomDescription(patientDTO.getSymptomDescription())
                .departmentName(appointment.getDepartment().getName()) // 진료과목
                .contact(patientDTO.getContact())
                .build();
    }
}