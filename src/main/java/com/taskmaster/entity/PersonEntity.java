package com.taskmaster.entity;

import com.taskmaster.entity.enums.AuthEnum;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private AuthEnum provider;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
