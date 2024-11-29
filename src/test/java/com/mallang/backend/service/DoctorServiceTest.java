package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private MultipartFile photo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDoctors() {
        // Given
        Doctor doctor1 = Doctor.builder().id(1L).name("Dr. Smith").specialty("Cardiology").contact("123456").photoPath("path1").build();
        Doctor doctor2 = Doctor.builder().id(2L).name("Dr. Jones").specialty("Neurology").contact("789012").photoPath("path2").build();
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        // When
        List<DoctorDTO> result = doctorService.getAllDoctors();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dr. Smith", result.get(0).getName());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testCreateDoctor() {
        // Given
        DoctorDTO doctorDTO = DoctorDTO.builder().name("Dr. Kim").specialty("Pediatrics").contact("5551234").build();
        Doctor savedDoctor = Doctor.builder().id(1L).name("Dr. Kim").specialty("Pediatrics").contact("5551234").photoPath("uploaded/photo/path").build();

        when(doctorRepository.save(any(Doctor.class))).thenReturn(savedDoctor);
        when(photo.getOriginalFilename()).thenReturn("photo.jpg");
        when(photo.isEmpty()).thenReturn(false);

        // When
        doctorService.createDoctor(doctorDTO, photo);

        // Then
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testUpdateDoctor() {
        // Given
        Long doctorId = 1L;
        Doctor existingDoctor = Doctor.builder()
                .id(doctorId)
                .name("Dr. Lee")
                .specialty("Dermatology")
                .contact("5555678")
                .photoPath("old/path.jpg")
                .build();

        DoctorDTO updateDTO = DoctorDTO.builder()
                .name("Dr. Lee Updated")
                .specialty("Cosmetic Surgery")
                .contact("5555678")
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        when(photo.getOriginalFilename()).thenReturn("updated_photo.jpg");
        when(photo.isEmpty()).thenReturn(false);

        // Mock savePhoto to return a fake path
        DoctorService spyService = spy(doctorService);
        doReturn("mocked/path/to/photo.jpg").when(spyService).savePhoto(photo);

        // When
        spyService.updateDoctor(doctorId, updateDTO, photo);

        // Then
        assertEquals("Dr. Lee Updated", existingDoctor.getName());
        assertEquals("Cosmetic Surgery", existingDoctor.getSpecialty());
        assertEquals("mocked/path/to/photo.jpg", existingDoctor.getPhotoPath());
        verify(doctorRepository, times(1)).save(existingDoctor);
    }

    @Test
    void testDeleteDoctor() {
        // Given
        Long doctorId = 1L;
        doNothing().when(doctorRepository).deleteById(doctorId);

        // When
        doctorService.deleteDoctor(doctorId);

        // Then
        verify(doctorRepository, times(1)).deleteById(doctorId);
    }
}