package com.taskmaster.dto.task.response;

import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.entity.enums.TaskTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RetrieveTaskFilterResponseDto {

    private UUID id;
    private String title;
    private String description;
    private TaskTypeEnum taskType;
    private TaskStatusEnum status;
    private boolean done;
    private LocalDateTime createdAt;
    private LocalDateTime deadlineAt;
    private LocalDateTime nextRunAt;
    private long secondsTimeLeft;
}
