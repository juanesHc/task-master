package com.taskmaster.dto.auth.response;

import com.taskmaster.entity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private long expiresInSeconds;
    private UUID personId;
    private String email;
    private RoleEnum role;
}
