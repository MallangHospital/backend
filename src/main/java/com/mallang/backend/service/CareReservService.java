package com.mallang.backend.service;

import com.mallang.backend.domain.CareReserv;
import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.dto.CareReservDTO;
import com.mallang.backend.repository.CareReservRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CareReservService {
    @Autowired
    private CareReservRepository careReservRepository;
    private final ModelMapper modelMapper;


    public boolean checkAvailability(LocalDateTime appointmentDateTime, Doctor doctor) {
        // 해당 의사와 시간에 예약이 있는지 확인
        return careReservRepository.findByAppointmentDateTimeAndDoctor(appointmentDateTime, doctor) == null;
    }

    public void createReserv(CareReservDTO careReservDTO) {
        CareReserv careReserv = modelMapper.map(careReservDTO, CareReserv.class);
        boolean isAvailable = checkAvailability(careReserv.getAppointmentDateTime(), careReserv.getDoctor());

        // 예약 불가능한 경우 예외 처리
        if (!isAvailable) {
            throw new IllegalStateException("이미 마감된 시간대입니다.");
        }

        careReservRepository.save(careReserv);
    }


}
