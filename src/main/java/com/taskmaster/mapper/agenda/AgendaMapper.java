package com.taskmaster.mapper.agenda;

import com.taskmaster.dto.agenda.AgendaTaskDto;
import com.taskmaster.entity.TaskEntity;
import org.springframework.stereotype.Component;


@Component
public class AgendaMapper {

    public AgendaTaskDto taskEntityToAgendaTaskDto(TaskEntity taskEntity){
        AgendaTaskDto agendaTaskDto=new AgendaTaskDto();

        agendaTaskDto.setTitle(taskEntity.getTitle());
        agendaTaskDto.setDescription(taskEntity.getDescription());
        agendaTaskDto.setExpirationDate(taskEntity.getExpirationDate());
        agendaTaskDto.setExpirationTime(taskEntity.getExpirationTime());

        return agendaTaskDto;
    }

}
