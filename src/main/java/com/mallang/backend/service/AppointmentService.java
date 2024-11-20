package com.mallang.backend.service;

import com.mallang.backend.domain.*;
import com.mallang.backend.dto.AppointmentDTO;
import com.mallang.backend.repository.*;
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
    private final AvailableTimeRepository availableTimeRepository;

    // 예약 생성
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // 의사 정보 검증
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));

        // 부서 정보 검증
        Department department = departmentRepository.findById(appointmentDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // 예약 날짜와 시간의 가용성 확인
        LocalDate date = appointmentDTO.getAppointmentDate();
        LocalTime time = appointmentDTO.getAppointmentTime();

        // 스케줄 확인
        Schedule schedule = scheduleRepository.findByDoctorAndDate(doctor, date)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found for the doctor on the selected date."));

        // AvailableTime 확인 및 예약 가능 여부 체크
        AvailableTime availableTime = availableTimeRepository.findByScheduleAndTime(schedule, time)
                .orElseThrow(() -> new IllegalStateException("The selected time is not available for this doctor."));

        if (availableTime.isReserved()) {
            throw new IllegalStateException("The selected time slot is already reserved.");
        }

        // 예약 생성
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .department(department)
                .memberId(appointmentDTO.getMemberId()) // `AppointmentDTO`에서 가져오기
                .appointmentDate(date)
                .appointmentTime(time)
                .symptomDescription(appointmentDTO.getSymptomDescription())
                .build();

        // AvailableTime 예약 상태 업데이트
        availableTime.setReserved(true);
        availableTimeRepository.save(availableTime);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    // 특정 의사의 특정 날짜에 예약 불가 시간 목록 조회
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