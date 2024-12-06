package com.mallang.backend.domain;

import com.mallang.backend.domain.AvailableTime;
import com.mallang.backend.domain.BaseEntity;
import com.mallang.backend.domain.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTime> availableTimes;

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
        for (AvailableTime availableTime : availableTimes) {
            availableTime.setSchedule(this); // 양방향 관계 설정
        }
    }
}