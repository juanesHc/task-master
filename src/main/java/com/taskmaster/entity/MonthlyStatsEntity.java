package com.taskmaster.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "monthly_stats", uniqueConstraints = {
        @UniqueConstraint(name = "uk_stats_person_period", columnNames = {"person_id", "year_month_value"})
})
@Getter
@Setter
public class MonthlyStatsEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity personEntity;

    @Column(name = "year_month_value", nullable = false, length = 7)
    private String yearMonth;

    @Column(name = "total_tasks", nullable = false)
    private int totalTasks;

    @Column(name = "completed_tasks", nullable = false)
    private int completedTasks;

    @Column(name = "missed_tasks", nullable = false)
    private int missedTasks;

    @Column(name = "completion_rate", nullable = false)
    private double completionRate;
}
