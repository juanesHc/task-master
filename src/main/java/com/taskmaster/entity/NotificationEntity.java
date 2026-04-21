package com.taskmaster.entity;

import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "ix_notification_person", columnList = "person_id"),
        @Index(name = "ix_notification_task", columnList = "task_id"),
        @Index(name = "ix_notification_scheduled", columnList = "scheduled_at")
})
@Getter
@Setter
public class NotificationEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity personEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity taskEntity;

    @Column(nullable = false, length = 500)
    private String notificationMessage;

    @Column(nullable = false)
    private boolean read = false;

    @Column(nullable = false)
    private boolean sent = false;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTypeEnum notificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannelEnum channel = NotificationChannelEnum.IN_APP;
}
