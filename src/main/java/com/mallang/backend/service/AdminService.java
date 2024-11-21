package com.mallang.backend.service;

import com.mallang.backend.dto.HealthcareReserveDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final HealthcareReserveService healthcareReserveService;

    // HealthcareReserveService를 주입받음
    public AdminService(HealthcareReserveService healthcareReserveService) {
        this.healthcareReserveService = healthcareReserveService;
    }

    // 의료진 등록
    public void registerDoctor(String name, String specialty, String contact) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("의료진 이름이 입력되지 않았습니다..");
        }
        if (specialty == null || specialty.isEmpty()) {
            throw new IllegalArgumentException("전문 분야를 입력해야 합니다.");
        }
        if (contact == null || contact.isEmpty()) {
            throw new IllegalArgumentException("연락처를 입력해야 합니다.");
        }

        // 의료진 등록 로직 (DB에 저장하는 코드 등)
        System.out.println("의료진 정보 등록: " + name + ", " + specialty + ", 연락처: " + contact);
    }

    // 의료진 정보 수정
    public void updateDoctor(int doctorId, String name, String specialty, String contact) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해야 합니다.");
        }
        if (specialty == null || specialty.isEmpty()) {
            throw new IllegalArgumentException("전문 분야를 입력해야 합니다.");
        }
        if (contact == null || contact.isEmpty()) {
            throw new IllegalArgumentException("연락처를 입력해야 합니다.");
        }

        // 의료진 정보 수정 로직 (DB에서 해당 의료진 정보 업데이트)
        System.out.println("의료진 정보 수정: " + doctorId + ", " + name + ", " + specialty + ", 연락처: " + contact);
    }

    // 의료진 정보 삭제
    public void deleteDoctor(int doctorId) {
        // 의료진 삭제 로직 (DB에서 해당 의료진 정보 삭제)
        System.out.println("의료진 정보 삭제: " + doctorId);
    }

    // 의료진 휴진 정보 등록
    public void registerVacation(int doctorId, String startDate, String endDate) {
        if (startDate == null || startDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 시작일을 입력해야 합니다.");
        }
        if (endDate == null || endDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 종료일을 입력해야 합니다.");
        }

        // 휴진 정보 등록 로직 (DB에 등록)
        System.out.println("휴진 정보 등록: " + doctorId + ", " + startDate + " - " + endDate);
    }

    // 의료진 휴진 정보 수정
    public void updateVacation(int vacationId, String startDate, String endDate) {
        if (startDate == null || startDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 시작일을 입력해야 합니다.");
        }
        if (endDate == null || endDate.isEmpty()) {
            throw new IllegalArgumentException("휴진 종료일을 입력해야 합니다.");
        }

        // 휴진 정보 수정 로직 (DB에서 해당 휴진 정보 수정)
        System.out.println("휴진 정보 수정: " + vacationId + ", " + startDate + " - " + endDate);
    }

    // 의료진 휴진 정보 삭제
    public void deleteVacation(int vacationId) {
        // 휴진 정보 삭제 로직 (DB에서 해당 휴진 정보 삭제)
        System.out.println("휴진 정보 삭제: " + vacationId);
    }

    // 건의사항 상태 업데이트 (읽음, 처리 중, 완료 등)
    public void updateInquiryStatus(int inquiryId, String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("상태를 입력해야 합니다.");
        }

        // 건의사항 상태 업데이트 로직 (DB에서 해당 건의사항 상태 변경)
        System.out.println("건의사항 상태 업데이트: " + inquiryId + ", 상태: " + status);
    }

    // 공지사항 등록
    public void registerNotice(String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("본문을 입력해야 합니다.");
        }

        // 공지사항 등록 로직 (DB에 저장)
        System.out.println("공지사항 등록: " + title);
    }

    // 공지사항 수정
    public void updateNotice(int noticeId, String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("본문을 입력해야 합니다.");
        }

        // 공지사항 수정 로직 (DB에서 해당 공지사항 수정)
        System.out.println("공지사항 수정: " + noticeId);
    }

    // 공지사항 삭제
    public void deleteNotice(int noticeId) {
        // 공지사항 삭제 로직 (DB에서 해당 공지사항 삭제)
        System.out.println("공지사항 삭제: " + noticeId);
    }

    // 매거진 등록
    public void registerMagazine(String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("본문을 입력해야 합니다.");
        }

        // 매거진 등록 로직 (DB에 저장)
        System.out.println("매거진 등록: " + title);
    }

    // 매거진 수정
    public void updateMagazine(int magazineId, String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("본문을 입력해야 합니다.");
        }

        // 매거진 수정 로직 (DB에서 해당 매거진 수정)
        System.out.println("매거진 수정: " + magazineId);
    }

    // 매거진 삭제
    public void deleteMagazine(int magazineId) {
        // 매거진 삭제 로직 (DB에서 해당 매거진 삭제)
        System.out.println("매거진 삭제: " + magazineId);
    }

    // 환자 예약 정보 조회
    public void viewReservationDetails(int reservationId) {
        // 환자 예약 정보 조회 로직 (DB에서 해당 예약 정보 조회)
        System.out.println("예약 정보 조회: " + reservationId);
    }
    // Healthcare 예약 확인 목록 조회
    public List<HealthcareReserveDTO> getHealthcareReservations() {
        // Healthcare 예약 목록 조회 로직 (서비스 계층에서 조회)
        return healthcareReserveService.getAllReservations();
    }

    // Healthcare 예약 상세 정보 조회
    @Transactional(readOnly = true)
    public HealthcareReserveDTO getHealthcareReservationDetails(Long hId) {
        // Healthcare 예약 상세 정보 조회 로직
        return healthcareReserveService.getHealthcareReservationDetails(hId);
    }

    // 환자 예약 정보 조회
    public void viewReservationDetails(int reservationId) {
        // 환자 예약 정보 조회 로직 (DB에서 해당 예약 정보 조회)
        System.out.println("예약 정보 조회: " + reservationId);
    }

}