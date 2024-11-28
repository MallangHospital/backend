package com.mallang.backend.service;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    // 모든 예약 조회
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 회원 ID로 예약 조회
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByMemberId(String memberId) {
        List<Appointment> appointments = appointmentRepository.findByMember_Mid(memberId);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 예약 ID로 예약 조회
    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }

    // 예약 취소
    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found."));
        appointmentRepository.delete(appointment);
    }

    // DTO 변환 메서드
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getName())
                .departmentId(appointment.getDepartment().getId())
                .departmentName(appointment.getDepartment().getName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .symptomDescription(appointment.getSymptomDescription())
                .patientName(appointment.getMember().getName())
                .contact(appointment.getMember().getPhoneNum()) // 환자 연락처 포함
                .status(appointment.getStatus())
                .build();
    }
}