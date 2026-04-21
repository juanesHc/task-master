package com.taskmaster.dto.agenda;

import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.entity.enums.TaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaTaskDto {

    private UUID id;
    private String title;
    private String description;
    private TaskTypeEnum taskType;
    private TaskStatusEnum status;
    private LocalDateTime deadlineAt;
    private LocalDateTime nextRunAt;
}
