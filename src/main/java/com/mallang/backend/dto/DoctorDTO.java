package com.mallang.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class DoctorDTO {
    private String name;
    private String position;
    private ArrayList<String> history;
    private String departmentName;
}