package com.mallang.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자
public class PatientDTO {

    private Long id;                   // 환자 ID
    private String name;               // 환자 이름
    private String contact;            // 환자 연락처
    private String symptomDescription; // 환자 증상 설명
    private String birthDate;          // 환자 생년월일 (필요시)
    private String address;            // 환자 주소 (필요시)
    private String gender;             // 환자 성별 (필요시)

    @Builder
    public PatientDTO(Long id, String name, String contact, String symptomDescription, String birthDate, String address, String gender) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.symptomDescription = symptomDescription;
        this.birthDate = birthDate;
        this.address = address;
        this.gender = gender;
    }

    // Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "PatientDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", symptomDescription='" + symptomDescription + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}