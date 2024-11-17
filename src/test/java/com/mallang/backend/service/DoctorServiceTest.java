package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository; // mock DoctorRepository

    @InjectMocks
    private DoctorService doctorService; // DoctorService에 mock된 DoctorRepository를 주입

    @LocalServerPort
    private int port;

    @Test
    void testGetAllDoctors() {
        System.out.println("임시로 설정된 포트 번호: "+ port);
        // Given: 테스트 데이터 준비
        Doctor doctor1 = new Doctor(
                1L, // id
                "Dr. Smith", // 이름
                "Surgeon", // 전문 분야
                "123-456-7890", // 연락처
                "http://example.com/photo1.jpg", // 사진 URL
                LocalDate.of(2024, 12, 1), // 휴진 시작일
                LocalDate.of(2024, 12, 10), // 휴진 종료일
                Arrays.asList("Surgery", "Consulting"), // 경력
                null // 부서 (Department)
        );

        Doctor doctor2 = new Doctor(
                2L, // id
                "Dr. John", // 이름
                "Pediatrician", // 전문 분야
                "987-654-3210", // 연락처
                "http://example.com/photo2.jpg", // 사진 URL
                LocalDate.of(2024, 12, 15), // 휴진 시작일
                LocalDate.of(2024, 12, 20), // 휴진 종료일
                Arrays.asList("Children Care", "Vaccinations"), // 경력
                null // 부서 (Department)
        );

        // mock DoctorRepository가 findAll을 호출할 때 위 데이터를 반환하도록 설정
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트에 대한 검증
        assertNotNull(doctorDTOList); // 반환값이 null이 아님을 확인
        assertEquals(2, doctorDTOList.size()); // 리스트에 두 개의 DoctorDTO가 있어야 함

        // 첫 번째 DoctorDTO 검증
        DoctorDTO doctorDTO1 = doctorDTOList.get(0);
        assertEquals("Dr. Smith", doctorDTO1.getName());
        assertEquals("Surgeon", doctorDTO1.getPosition());
        assertEquals("123-456-7890", doctorDTO1.getPhoneNumber());
        assertEquals("http://example.com/photo1.jpg", doctorDTO1.getPhotoUrl());
        assertEquals("2024-12-01", doctorDTO1.getVacationStartDate());
        assertEquals("2024-12-10", doctorDTO1.getVacationEndDate());
        assertEquals(Arrays.asList("Surgery", "Consulting"), doctorDTO1.getHistory());
        assertNull(doctorDTO1.getDepartmentName()); // 부서 이름은 null이어야 함

        // 두 번째 DoctorDTO 검증
        DoctorDTO doctorDTO2 = doctorDTOList.get(1);
        assertEquals("Dr. John", doctorDTO2.getName());
        assertEquals("Pediatrician", doctorDTO2.getPosition());
        assertEquals("987-654-3210", doctorDTO2.getPhoneNumber());
        assertEquals("http://example.com/photo2.jpg", doctorDTO2.getPhotoUrl());
        assertEquals("2024-12-15", doctorDTO2.getVacationStartDate());
        assertEquals("2024-12-20", doctorDTO2.getVacationEndDate());
        assertEquals(Arrays.asList("Children Care", "Vaccinations"), doctorDTO2.getHistory());
        assertNull(doctorDTO2.getDepartmentName()); // 부서 이름은 null이어야 함

        // Verify: mock 메서드가 호출되었는지 확인
        verify(doctorRepository, times(1)).findAll(); // findAll() 메서드가 정확히 한 번 호출되었는지 확인
    }

    @Test
    void testGetAllDoctors_emptyList() {
        // Given: 빈 리스트 반환 설정
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트가 비어 있는지 확인
        assertNotNull(doctorDTOList); // 리스트가 null이 아님을 확인
        assertTrue(doctorDTOList.isEmpty()); // 리스트가 비어 있어야 함
    }
}