package com.mallang.backend.service;

import com.mallang.backend.domain.Appointment;
import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.repository.AppointmentRepository;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final ScheduleRepository scheduleRepository;

    // 예약 생성
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, String memberId) {
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));
        Department department = departmentRepository.findById(appointmentDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // 예약 날짜와 시간의 가용성 확인
        LocalDate date = appointmentDTO.getAppointmentDate();
        LocalTime time = appointmentDTO.getAppointmentTime();
        boolean isAvailable = scheduleRepository.findByDoctorAndDate(doctor, date)
                .map(schedule -> schedule.getAvailableTimes().contains(time))
                .orElse(false);

        if (!isAvailable) {
            throw new IllegalStateException("The selected time is not available for this doctor.");
        }

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .department(department)
                .memberId(memberId) // String 타입 memberId
                .appointmentDate(date)
                .appointmentTime(time)
                .symptomDescription(appointmentDTO.getSymptomDescription())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    @Transactional(readOnly = true)
    public List<String> getUnavailableTimes(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));

        List<Appointment> appointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, date);

        return appointments.stream()
                .map(appointment -> appointment.getAppointmentTime().toString())
                .collect(Collectors.toList());
    }

    // Appointment 엔티티를 AppointmentDTO로 변환하는 메서드
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .doctorId(appointment.getDoctor().getId())
                .departmentId(appointment.getDepartment().getId())
                .memberId(appointment.getMemberId()) // String 타입 memberId
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .symptomDescription(appointment.getSymptomDescription())
                .build();
    }
}