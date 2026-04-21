package com.taskmaster.entity;

import com.taskmaster.entity.enums.ExecutionOutcomeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_execution_log", indexes = {
        @Index(name = "ix_exec_task", columnList = "task_id"),
        @Index(name = "ix_exec_person_month", columnList = "person_id,recorded_at")
})
@Getter
@Setter
public class TaskExecutionLogEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity taskEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity personEntity;

    @Column(name = "deadline_at", nullable = false)
    private LocalDateTime deadlineAt;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionOutcomeEnum outcome;
}
