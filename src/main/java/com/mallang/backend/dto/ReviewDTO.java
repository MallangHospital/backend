package com.mallang.backend.dto;

public class ReviewDTO {
    private Long id;
    private String content;
    private int star;
    private Long doctorId; // 리뷰 대상 의사 ID
    private Long memberId; // 리뷰 작성자 ID

    // 기본 생성자
    public ReviewDTO() {}

    // 생성자
    public ReviewDTO(Long id, String content, int star, Long doctorId, Long memberId) {
        this.id = id;
        this.content = content;
        this.star = star;
        this.doctorId = doctorId;
        this.memberId = memberId;
    }

    public ReviewDTO(Long id, String content, int star, Long id1, String mid) {
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}