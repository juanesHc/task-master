package com.taskmaster.entity;

import com.taskmaster.entity.enums.NotificationEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="notifications")
@Getter
@Setter
public class NotificationEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    private String notificationMessage;
    
    private boolean read;

    @Enumerated(EnumType.STRING)
    private NotificationEnum notificationType;
}
