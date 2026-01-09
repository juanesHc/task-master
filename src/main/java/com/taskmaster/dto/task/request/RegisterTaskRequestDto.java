package com.taskmaster.dto.task.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RegisterTaskRequestDto {

    private UUID personId;

    private String title;

    private String description;

    private LocalDate expirationDate;

    private LocalTime expirationTime;
}
