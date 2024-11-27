package com.mallang.backend.repository;

import com.mallang.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByDepartmentId(Long departmentId);
    boolean existsByDoctorId(Long doctorId);
}