package com.mallang.backend.repository;

import com.mallang.backend.domain.OnlineRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineRegistrationRepository extends JpaRepository<OnlineRegistration, Long> {
    // 기본적인 CRUD 기능은 JpaRepository에서 제공
}