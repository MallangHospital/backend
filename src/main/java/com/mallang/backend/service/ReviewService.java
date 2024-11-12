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
                .map(review -> new ReviewDTO(review.getId(), review.getContent(), review.getStar(),
                        review.getDoctor().getId(), review.getMember().getMid()))
                .collect(Collectors.toList());
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO, Doctor doctor, Member member) {
        Review review = new Review(reviewDTO.getContent(), reviewDTO.getStar(), doctor, member);
        review = reviewRepository.save(review);
        return new ReviewDTO(review.getId(), review.getContent(), review.getStar(),
                review.getDoctor().getId(), review.getMember().getMid());
    }
}