package com.taskmaster.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterPersonRequestDto {
    private String givenName;
    private String familyName;
    private String password;
    private String email;
}
