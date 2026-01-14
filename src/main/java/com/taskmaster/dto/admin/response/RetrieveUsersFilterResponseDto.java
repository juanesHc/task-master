package com.taskmaster.dto.admin.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RetrieveUsersFilterResponseDto {

    private String givenName;
    private String familyName;
    private String email;
    private String provider;
    private String roleName;
    private LocalDate createdAt;
}
