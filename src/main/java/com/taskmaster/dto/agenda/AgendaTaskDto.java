package com.taskmaster.dto.agenda;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AgendaTaskDto {

    private String title;
    private String description;
    private LocalDate expirationDate;
    private LocalTime expirationTime;
}
