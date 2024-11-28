package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class MemberJoinDTO {
    private String mid;
    private String mpw;
    private String email;
    private String name;
    private String phoneNum; //휴대폰번호
    private String rrn; //주민등록번호
}