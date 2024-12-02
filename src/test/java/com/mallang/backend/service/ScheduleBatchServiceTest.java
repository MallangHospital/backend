/*
package com.mallang.backend.service;

import com.mallang.backend.domain.Schedule;
import com.mallang.backend.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Commit
class ScheduleBatchServiceTest {

    @Autowired
    private ScheduleBatchService scheduleBatchService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void testGenerateSchedulesForNextMonth() {
        Long doctorId = 1L; // 적절한 의사 ID 설정
        scheduleBatchService.generateSchedulesForNextMonth(doctorId);

        List<Schedule> schedules = scheduleRepository.findAll();
        assertFalse(schedules.isEmpty(), "Schedules should not be empty after generation");
    }
}

 */