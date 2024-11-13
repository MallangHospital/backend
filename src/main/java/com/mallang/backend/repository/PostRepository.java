/*package com.mallang.backend.repository;

import com.mallang.backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Role.PostEntity,Long> {
    @Modifying
    @Query(value = "update PostEntity p set p.postHits=p.postHits+1 where p.id=:id")
    void updateHits(@Param("id") Long id);
}*/
