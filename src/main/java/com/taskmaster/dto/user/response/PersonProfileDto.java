package com.taskmaster.dto.user.response;

import com.taskmaster.entity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonProfileDto {
    private UUID id;
    private String givenName;
    private String familyName;
    private String email;
    private String picture;
    private RoleEnum role;
    private LocalDateTime createdAt;
}
