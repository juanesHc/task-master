package com.taskmaster.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
}
