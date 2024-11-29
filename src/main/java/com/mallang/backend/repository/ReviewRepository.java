package com.mallang.backend.repository;

import com.mallang.backend.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {

    // 특정 의사에 대한 리뷰 페이징 조회
    Page<Review> findByDoctorId(String doctorId, Pageable pageable);

    // 특정 사용자가 작성한 리뷰 여부 확인
    boolean existsByIdAndMemberId(String id, String memberId);
}