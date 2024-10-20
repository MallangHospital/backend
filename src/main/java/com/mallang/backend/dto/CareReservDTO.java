package com.mallang.backend.dto;

import com.mallang.backend.domain.Department;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.VisitType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CareReservDTO {
    Long cid;
    Member member;
    VisitType visitType;
    Department department;
    Doctor doctor;
    LocalDateTime appointmentDateTime;
    String symptoms;
}
