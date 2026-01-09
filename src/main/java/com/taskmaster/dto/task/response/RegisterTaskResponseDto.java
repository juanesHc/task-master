package com.taskmaster.dto.task.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterTaskResponseDto {

    private LocalDateTime timeLeft;

    private String successMessage;

}
