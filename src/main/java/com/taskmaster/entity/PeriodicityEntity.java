package com.taskmaster.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "periodicity")
@Getter
@Setter
public class PeriodicityEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false, unique = true)
    private TaskEntity taskEntity;

    @Column(name = "cron_expression", nullable = false, length = 120)
    private String cronExpression;

    @Column(name = "timezone", nullable = false, length = 64)
    private String timezone = "UTC";
}
