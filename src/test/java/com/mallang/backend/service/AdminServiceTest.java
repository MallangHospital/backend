package com.mallang.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        //adminService = new AdminService();
    }

    @Test
    void registerDoctor_validDetails() {
        assertDoesNotThrow(() -> adminService.registerDoctor("Dr. Kim", "Cardiology", "010-1234-5678"));
    }

    @Test
    void registerDoctor_invalidDetails() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> adminService.registerDoctor("", "Cardiology", "010-1234-5678"));
        assertEquals("의료진 이름이 입력되지 않았습니다.", exception.getMessage());
    }

    @Test
    void updateDoctor_validDetails() {
        assertDoesNotThrow(() -> adminService.updateDoctor(1, "Dr. Lee", "Dermatology", "010-9876-5432"));
    }

    @Test
    void deleteDoctor_validId() {
        assertDoesNotThrow(() -> adminService.deleteDoctor(1));
    }

    @Test
    void registerVacation_validDates() {
        assertDoesNotThrow(() -> adminService.registerVacation(1, "2024-12-01", "2024-12-15"));
    }

    @Test
    void registerVacation_invalidDates() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> adminService.registerVacation(1, "", "2024-12-15"));
        assertEquals("휴진 시작일을 입력해야 합니다.", exception.getMessage());
    }

    @Test
    void updateInquiryStatus_validStatus() {
        assertDoesNotThrow(() -> adminService.updateInquiryStatus(1, "Resolved"));
    }

    @Test
    void updateInquiryStatus_invalidStatus() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> adminService.updateInquiryStatus(1, ""));
        assertEquals("상태를 입력해야 합니다.", exception.getMessage());
    }

    @Test
    void registerNotice_validDetails() {
        assertDoesNotThrow(() -> adminService.registerNotice("Important Notice", "This is the content."));
    }

    @Test
    void registerNotice_invalidDetails() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> adminService.registerNotice("", "This is the content."));
        assertEquals("제목을 입력해야 합니다.", exception.getMessage());
    }

    @Test
    void registerMagazine_validDetails() {
        assertDoesNotThrow(() -> adminService.registerMagazine("Health Tips", "Stay healthy!"));
    }

    @Test
    void registerMagazine_invalidDetails() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> adminService.registerMagazine("Health Tips", ""));
        assertEquals("본문을 입력해야 합니다.", exception.getMessage());
    }
}