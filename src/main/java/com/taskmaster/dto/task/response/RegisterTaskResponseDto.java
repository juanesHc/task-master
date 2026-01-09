package com.taskmaster.dto.task.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterTaskResponseDto {

    private long secondsTimeLeft;

    private String successMessage;

}
