package com.taskmaster.dto.task.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RetrieveTaskFilterResponseDto {

    private Boolean done;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate expiration;
    private long SecondsTimeLeft;



}
