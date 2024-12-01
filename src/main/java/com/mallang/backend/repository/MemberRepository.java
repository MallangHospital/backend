package com.mallang.backend.repository;

import com.mallang.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsById(String id); // 필요에 따라 ID 필드 이름 수정
}