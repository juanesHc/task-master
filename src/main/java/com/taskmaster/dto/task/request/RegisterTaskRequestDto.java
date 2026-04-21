package com.taskmaster.dto.task.request;

import com.taskmaster.entity.enums.TaskTypeEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Task type is required")
    private TaskTypeEnum taskType = TaskTypeEnum.ONE_TIME;

    @NotNull(message = "deadlineAt is required (ISO date-time)")
    @Future(message = "deadlineAt must be in the future")
    private LocalDateTime deadlineAt;

    private String cronExpression;

    private String timezone;
}
