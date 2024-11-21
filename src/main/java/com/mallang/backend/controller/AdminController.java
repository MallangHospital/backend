package com.mallang.backend.controller;

import com.mallang.backend.dto.AppointmentDTO;

import com.mallang.backend.dto.HealthcareReserveDTO;
import com.mallang.backend.service.AdminService;
import com.mallang.backend.service.HealthcareReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final HealthcareReserveService healthcareReserveService;
    // HealthcareReserveService를 AdminController에 주입
    public AdminController(HealthcareReserveService healthcareReserveService) {
        this.healthcareReserveService = healthcareReserveService;
    }

    @Autowired
    private AdminService adminService;

    // 의료진 등록
    @PostMapping("/doctors")
    public String registerDoctor(@RequestParam String name, @RequestParam String specialty, @RequestParam String contact) {
        try {
            adminService.registerDoctor(name, specialty, contact);
            return "의료진 등록이 완료되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 수정
    @PutMapping("/doctors/{doctorId}")
    public String updateDoctor(@PathVariable int doctorId, @RequestParam String name, @RequestParam String specialty, @RequestParam String contact) {
        try {
            adminService.updateDoctor(doctorId, name, specialty, contact);
            return "의료진 정보가 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 삭제
    @DeleteMapping("/doctors/{doctorId}")
    public String deleteDoctor(@PathVariable int doctorId) {
        adminService.deleteDoctor(doctorId);
        return "의료진 정보가 삭제되었습니다.";
    }

    // 의료진 휴진 정보 등록
    @PostMapping("/vacations")
    public String registerVacation(@RequestParam int doctorId, @RequestParam String startDate, @RequestParam String endDate) {
        try {
            adminService.registerVacation(doctorId, startDate, endDate);
            return "의료진 휴진 정보가 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 휴진 정보 수정
    @PutMapping("/vacations/{vacationId}")
    public String updateVacation(@PathVariable int vacationId, @RequestParam String startDate, @RequestParam String endDate) {
        try {
            adminService.updateVacation(vacationId, startDate, endDate);
            return "휴진 정보가 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 의료진 휴진 정보 삭제
    @DeleteMapping("/vacations/{vacationId}")
    public String deleteVacation(@PathVariable int vacationId) {
        adminService.deleteVacation(vacationId);
        return "휴진 정보가 삭제되었습니다.";
    }

    // 건의사항 상태 변경 (읽음, 처리 중, 완료)
    @PutMapping("/inquiries/{inquiryId}/status")
    public String updateInquiryStatus(@PathVariable int inquiryId, @RequestParam String status) {
        try {
            adminService.updateInquiryStatus(inquiryId, status);
            return "건의사항 상태가 변경되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 공지사항 등록
    @PostMapping("/notices")
    public String registerNotice(@RequestParam String title, @RequestParam String content) {
        try {
            adminService.registerNotice(title, content);
            return "공지사항이 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 공지사항 수정
    @PutMapping("/notices/{noticeId}")
    public String updateNotice(@PathVariable int noticeId, @RequestParam String title, @RequestParam String content) {
        try {
            adminService.updateNotice(noticeId, title, content);
            return "공지사항이 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public String deleteNotice(@PathVariable int noticeId) {
        adminService.deleteNotice(noticeId);
        return "공지사항이 삭제되었습니다.";
    }

    // 매거진 등록
    @PostMapping("/magazines")
    public String registerMagazine(@RequestParam String title, @RequestParam String content) {
        try {
            adminService.registerMagazine(title, content);
            return "건강 매거진이 등록되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 매거진 수정
    @PutMapping("/magazines/{magazineId}")
    public String updateMagazine(@PathVariable int magazineId, @RequestParam String title, @RequestParam String content) {
        try {
            adminService.updateMagazine(magazineId, title, content);
            return "건강 매거진이 수정되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // 매거진 삭제
    @DeleteMapping("/magazines/{magazineId}")
    public String deleteMagazine(@PathVariable int magazineId) {
        adminService.deleteMagazine(magazineId);
        return "건강 매거진이 삭제되었습니다.";
    }

    // 예약 정보 목록 조회
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointments() {
        List<AppointmentDTO> appointments = adminService.getAppointments();
        return ResponseEntity.ok(appointments);
    }

    // 접수 확인 조회 구현중...

    // Healthcare 예약 확인 목록 조회
    @GetMapping("/healthcareReserves")
    public ResponseEntity<List<HealthcareReserveDTO>> getHealthcareReservations() {
        List<HealthcareReserveDTO> reservations = healthcareReserveService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }


    // 예약 상세 정보 조회
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentDetails(@PathVariable int appointmentId) {
        AppointmentDTO appointmentDetails = adminService.getAppointmentDetails(appointmentId);
        return ResponseEntity.ok(appointmentDetails);
    }
}
