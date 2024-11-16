package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository; // mock DoctorRepository

    @InjectMocks
    private DoctorService doctorService; // DoctorService에 mock된 DoctorRepository를 주입

    @Test
    void testGetAllDoctors() {
        // Given: 테스트 데이터 준비
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();

        // mock DoctorRepository가 findAll을 호출할 때 위 의사 데이터를 반환하도록 설정
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트에 대한 검증
        assertNotNull(doctorDTOList); // 반환값이 null이 아님을 확인
        assertEquals(2, doctorDTOList.size()); // 리스트에 두 개의 DoctorDTO가 있어야 함

        // 첫 번째 DoctorDTO 검증
        DoctorDTO doctorDTO1 = doctorDTOList.get(0);
        assertEquals("Dr. Smith", doctorDTO1.getName()); // 이름 검증
        assertEquals("Surgeon", doctorDTO1.getPosition()); // 직책 검증
        assertEquals(Arrays.asList("Surgery", "Consulting"), doctorDTO1.getHistory()); // 히스토리 검증
        assertNull(doctorDTO1.getDepartmentName()); // Department는 null이어야 함

        // 두 번째 DoctorDTO 검증
        DoctorDTO doctorDTO2 = doctorDTOList.get(1);
        assertEquals("Dr. John", doctorDTO2.getName()); // 이름 검증
        assertEquals("Pediatrician", doctorDTO2.getPosition()); // 직책 검증
        assertEquals(Arrays.asList("Children Care", "Vaccinations"), doctorDTO2.getHistory()); // 히스토리 검증
        assertNull(doctorDTO2.getDepartmentName()); // Department는 null이어야 함

        // Verify: mock 메서드가 호출되었는지 확인
        verify(doctorRepository, times(1)).findAll(); // findAll() 메서드가 정확히 한 번 호출되었는지 확인
    }

    @Test
    void testGetAllDoctors_emptyList() {
        // Given: 빈 리스트 반환 설정
        when(doctorRepository.findAll()).thenReturn(Arrays.asList());

        // When: 서비스 메서드 호출
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();

        // Then: 반환된 리스트가 비어 있는지 확인
        assertNotNull(doctorDTOList);
        assertTrue(doctorDTOList.isEmpty()); // 리스트가 비어 있어야 함
    }
}