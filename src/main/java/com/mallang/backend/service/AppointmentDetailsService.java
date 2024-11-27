package com.mallang.backend.service;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.dto.PatientDTO;
import com.mallang.backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentDetailsService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentDetailsService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // 모든 예약 정보와 환자 세부사항 조회
    public List<AppointmentDTO> getAllAppointments() {

        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO) // Appointment 엔티티를 AppointmentDTO로 변환
                .collect(Collectors.toList());
    }

    // Appointment 엔티티를 AppointmentDTO로 변환
    private AppointmentDTO convertToDTO(Appointment appointment) {
        // 환자 정보를 담는 PatientDTO 생성
        PatientDTO patientDTO = PatientDTO.builder()
                .id(Long.valueOf(appointment.getMember().getMid())) // 환자 ID
                .name(appointment.getMember().getName()) // 환자 이름
                .contact(appointment.getMember().getPhoneNum()) // 환자 연락처
                .symptomDescription(appointment.getSymptomDescription()) // 환자 증상
                .build();

        // AppointmentDTO에 환자 세부사항 포함
        return AppointmentDTO.builder()
                .appointmentTime(LocalTime.ofSecondOfDay(appointment.getAppointmentTime().getSecond()))
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .patientName(patientDTO.getName()) // 환자 이름 포함
                .patientName(String.valueOf(patientDTO)) // 환자 세부사항 포함
                .build();
    }
}