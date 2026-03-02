package com.taskmaster.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="person")
@Getter
@Setter
public class PersonEntity extends BaseEntity{

    private String givenName;

    private String familyName;

    private String email;

    private String password;


}
