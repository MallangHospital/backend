package com.mallang.backend.dto;

import java.util.ArrayList;

public class DoctorDTO {
    private String name;
    private String position;
    private ArrayList<String> history;
    private String departmentName;

    // Constructor, Getters and Setters
    public DoctorDTO(String name, String position, ArrayList<String> history, String departmentName) {
        this.name = name;
        this.position = position;
        this.history = history;
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}