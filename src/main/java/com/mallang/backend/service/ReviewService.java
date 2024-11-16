package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(review.getId());  // Setter로 필드 값 설정
                    dto.setContent(review.getContent());
                    dto.setStar(review.getStar());
                    dto.setDoctorId(review.getDoctor().getId());
                    dto.setMemberId(Long.valueOf(review.getMember().getMid()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO, Doctor doctor, Member member) {
        Review review = new Review();
        review.setContent(reviewDTO.getContent());  // Setter로 필드 값 설정
        review.setStar(reviewDTO.getStar());
        review.setDoctor(doctor);
        review.setMember(member);
        review = reviewRepository.save(review);

        // DTO 반환 시 기본 생성자 사용 후 Setter로 값 설정
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setStar(review.getStar());
        dto.setDoctorId(review.getDoctor().getId());
        dto.setMemberId(Long.valueOf(review.getMember().getMid()));
        return dto;
    }
}