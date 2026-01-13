package com.taskmaster.dto.task.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RetrieveTaskFilterRequestDto {

    private boolean done;
    private String title;
    private LocalDate createdAt;
    private LocalDate expiration;



}
