package com.mallang.backend.security;

import com.mallang.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomSecurityService {

    private final ReviewRepository reviewRepository;

    // 진료기록 여부 확인
    public boolean hasMedicalRecord(String username) {
        // 진료기록 로직 구현
        return true; // 예: 사용자가 진료기록이 있는 경우 true 반환
    }

    // 리뷰 작성자 확인
    public boolean isReviewOwner(String username, Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> review.getMemberId().equals(username))
                .orElse(false);
    }

    // 리뷰 작성자 또는 관리자 확인
    public boolean isReviewOwnerOrAdmin(String username, Long reviewId) {
        // 관리자 확인 로직 추가
        return isReviewOwner(username, reviewId) || username.equals("admin");
    }
}