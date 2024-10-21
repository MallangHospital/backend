package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.dto.DoctorDTO;
import com.mallang.backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getName(),
                doctor.getPosition(),
                (ArrayList<String>) doctor.getHistory(),
                doctor.getDepartment().getName() // Assuming Department has a name
        );
    }
}