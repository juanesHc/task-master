package com.taskmaster.dto.task.request;

import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.entity.enums.TaskTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RetrieveTaskFilterRequestDto {

    private Boolean done;
    private String title;
    private LocalDate createdAt;
    private LocalDate deadlineOn;
    private TaskStatusEnum status;
    private TaskTypeEnum taskType;
}
