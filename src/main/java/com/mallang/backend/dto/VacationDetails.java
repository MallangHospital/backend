package com.mallang.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationDetails {
    private String startDate;    // 휴진 시작일
    private String endDate;      // 휴진 종료일
}