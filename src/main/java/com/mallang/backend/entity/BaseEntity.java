package com.mallang.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false) //수정할 때는 관여 안하게
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false)//처음 작성할 때는 관여 안하게
    private LocalDateTime updatedTime;
}
