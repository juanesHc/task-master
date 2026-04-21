package com.taskmaster.entity;

import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.entity.enums.TaskTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task", indexes = {
        @Index(name = "ix_task_person", columnList = "person_id"),
        @Index(name = "ix_task_deadline", columnList = "deadline_at"),
        @Index(name = "ix_task_next_run", columnList = "next_run_at")
})
@Getter
@Setter
public class TaskEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity personEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskTypeEnum taskType = TaskTypeEnum.ONE_TIME;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatusEnum status = TaskStatusEnum.PENDING;

    @Column(name = "deadline_at", nullable = false)
    private LocalDateTime deadlineAt;

    @Column(name = "next_run_at")
    private LocalDateTime nextRunAt;

    @Column(name = "last_completed_at")
    private LocalDateTime lastCompletedAt;

    @OneToOne(mappedBy = "taskEntity", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private PeriodicityEntity periodicity;

    @Column(nullable = false)
    private boolean done = false;

    public boolean isPeriodic() {
        return taskType == TaskTypeEnum.PERIODIC;
    }
}
