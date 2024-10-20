package com.mallang.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareReserv {
    @Id
    Long cid;

    @ManyToOne
    Member member;

    @Enumerated(EnumType.STRING)
    VisitType visitType;

    @OneToOne
    Department department;

    @OneToOne
    Doctor doctor;

    LocalDateTime appointmentDateTime;

    String symptoms;
}
