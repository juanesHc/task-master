package com.taskmaster.mapper.agenda;

import com.taskmaster.dto.agenda.AgendaTaskDto;
import com.taskmaster.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgendaMapper {

    @Mapping(target = "deadlineAt", source = "deadlineAt")
    @Mapping(target = "nextRunAt", source = "nextRunAt")
    AgendaTaskDto toDto(TaskEntity entity);
}
