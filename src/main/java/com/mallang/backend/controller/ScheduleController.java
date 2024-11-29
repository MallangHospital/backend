package com.mallang.backend.controller;

import com.mallang.backend.dto.ScheduleDTO;
import com.mallang.backend.service.ScheduleBatchService;
import com.mallang.backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleBatchService scheduleBatchService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAvailableSchedules(
            @RequestParam Long doctorId,
            @RequestParam String date) {
        // Convert date from String to LocalDate
        LocalDate parsedDate = LocalDate.parse(date);

        // Fetch available schedules
        List<ScheduleDTO> availableSchedules = scheduleService.getAvailableSchedules(doctorId, parsedDate);

        return ResponseEntity.ok(availableSchedules);
    }

    @PostMapping("/generate-monthly")
    public ResponseEntity<?> generateSchedules(@RequestParam Long doctorId) {
        scheduleBatchService.generateSchedulesForNextMonth(doctorId);
        return ResponseEntity.ok("Schedules for the next month have been generated.");
    }
}