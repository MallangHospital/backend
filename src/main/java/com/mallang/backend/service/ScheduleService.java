package com.mallang.backend.service;

import com.mallang.backend.domain.Schedule;
import com.mallang.backend.dto.ScheduleDTO;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    // 특정 의사의 날짜별 예약 가능한 시간 조회
    public List<ScheduleDTO> getAvailableSchedules(Long doctorId, LocalDate date) {
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDate(doctorId, date);
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 의사의 예약 가능한 시간 목록 조회
    public List<LocalTime> getAvailableTimes(Long doctorId, LocalDate date) {
        // 스케줄 목록 조회
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDate(doctorId, date);

        // 예약 가능한 시간만 추출하여 반환
        return schedules.stream()
                .flatMap(schedule -> schedule.getAvailableTimes().stream())
                .collect(Collectors.toList());
    }

    // Schedule -> ScheduleDTO 변환 로직
    private ScheduleDTO convertToDTO(Schedule schedule) {
        return ScheduleDTO.builder()
                .doctorId(schedule.getDoctor().getId()) // Doctor의 ID 설정
                .date(schedule.getDate())
                .availableTimes(schedule.getAvailableTimes())
                .build();
    }
}