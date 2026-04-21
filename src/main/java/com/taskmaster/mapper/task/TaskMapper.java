package com.taskmaster.mapper.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.entity.PeriodicityEntity;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.enums.TaskStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personEntity", ignore = true)
    @Mapping(target = "periodicity", ignore = true)
    @Mapping(target = "status", expression = "java(com.taskmaster.entity.enums.TaskStatusEnum.PENDING)")
    @Mapping(target = "done", constant = "false")
    @Mapping(target = "nextRunAt", ignore = true)
    @Mapping(target = "lastCompletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TaskEntity fromRegisterRequest(RegisterTaskRequestDto dto);

    @Mapping(target = "secondsTimeLeft", source = "task", qualifiedByName = "secondsUntilDeadline")
    @Mapping(target = "successMessage", ignore = true)
    RegisterTaskResponseDto toRegisterResponse(TaskEntity task);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "secondsTimeLeft", source = ".", qualifiedByName = "secondsUntilDeadline")
    RetrieveTaskFilterResponseDto toFilterResponse(TaskEntity task);

    @Named("secondsUntilDeadline")
    default long secondsUntilDeadline(TaskEntity task) {
        if (task == null || task.getDeadlineAt() == null) {
            return 0L;
        }
        long seconds = Duration.between(LocalDateTime.now(), task.getDeadlineAt()).getSeconds();
        return Math.max(seconds, 0L);
    }
}
