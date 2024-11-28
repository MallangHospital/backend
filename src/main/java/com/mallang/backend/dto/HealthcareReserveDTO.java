package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HealthcareReserveDTO {
    private Long hId;
    private String name; // 이름
    private String memberId; // 회원 ID
    private String phoneNumber; // 휴대폰 번호
    private LocalDate reserveDate; // 예약 날짜
    private String hType; // 검진 구분
}