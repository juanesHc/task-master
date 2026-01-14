package com.taskmaster.dto.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RetrieveUsersFilterRequestDto {

    private String givenName;
    private String familyName;
    private String email;
    private String provider;
    private String roleName;
    private LocalDate createdAt;

}
