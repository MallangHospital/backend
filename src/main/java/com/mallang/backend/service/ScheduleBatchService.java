package com.mallang.backend.service;

import com.mallang.backend.domain.AvailableTime;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Schedule;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleBatchService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public void generateSchedulesForNextMonth(Long doctorId) {
        // 시작 날짜와 종료 날짜
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        // 의사 정보 확인
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));

        // 날짜별 스케줄 생성
        List<Schedule> schedules = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 월~금요일만 스케줄 생성
            if (date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5) {
                // 예약 가능한 시간 생성
                List<AvailableTime> availableTimes = new ArrayList<>();
                for (int hour = 9; hour <= 17; hour++) {
                    availableTimes.add(AvailableTime.builder()
                            .time(LocalTime.of(hour, 0))
                            .reserved(false)
                            .build());
                }

                // 스케줄 생성
                Schedule schedule = Schedule.builder()
                        .doctor(doctor)
                        .date(date)
                        .availableTimes(availableTimes)
                        .build();
                schedules.add(schedule);
            }
        }

        // 데이터베이스에 저장
        scheduleRepository.saveAll(schedules);
    }
}