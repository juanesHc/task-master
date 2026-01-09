package com.taskmaster.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="task")
@Getter
@Setter
public class TaskEntity extends BaseEntity {

    private String title;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    private String description;

    private LocalDate expirationDate;

    private LocalTime expirationTime;

    private boolean done;


}
