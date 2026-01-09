package com.taskmaster.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="person")
@Getter
@Setter
public class PersonEntity extends BaseEntity{

    private String givenName;

    private String familyName;

    private String email;

    private String password;

    @OneToMany(mappedBy = "person")
    private List<NotificationEntity> notifications;




}
