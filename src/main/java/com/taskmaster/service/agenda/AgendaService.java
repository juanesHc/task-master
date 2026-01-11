package com.taskmaster.service.agenda;

import com.taskmaster.dto.agenda.AgendaDto;
import com.taskmaster.dto.agenda.AgendaTaskDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.agenda.AgendaMapper;
import com.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final TaskRepository taskRepository;
    private final AgendaMapper agendaMapper;

    public AgendaDto retrieveAllTasks(String personId){

        List<TaskEntity> taskEntityList=taskRepository.findByPersonIdOrderByExpirationDateAscExpirationTimeAsc(UUID.fromString(personId));

        List<AgendaTaskDto> agendaTaskDtos=new ArrayList<>();
        taskEntityList.forEach(taskEntity -> agendaTaskDtos.add(agendaMapper.taskEntityToAgendaTaskDto(taskEntity)));

        AgendaDto agendaDto=new AgendaDto();
        agendaDto.setAgendaTaskDtoList(agendaTaskDtos);

        return agendaDto;

    }
}
