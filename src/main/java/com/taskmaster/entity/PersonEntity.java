package com.taskmaster.entity;

import com.taskmaster.entity.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person", indexes = {
        @Index(name = "ix_person_email", columnList = "email", unique = true),
        @Index(name = "ix_person_google_sub", columnList = "google_sub", unique = true)
})
@Getter
@Setter
public class PersonEntity extends BaseEntity {

    @Column(nullable = false)
    private String givenName;

    @Column(nullable = false)
    private String familyName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "google_sub", unique = true)
    private String googleSub;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role = RoleEnum.USER;

    @OneToMany(mappedBy = "personEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notificationEntities = new ArrayList<>();

    @OneToMany(mappedBy = "personEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> taskEntities = new ArrayList<>();
}
