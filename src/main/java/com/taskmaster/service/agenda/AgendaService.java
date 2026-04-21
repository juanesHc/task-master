package com.taskmaster.service.agenda;

import com.taskmaster.dto.agenda.AgendaDto;
import com.taskmaster.mapper.agenda.AgendaMapper;
import com.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgendaService {

    private final TaskRepository taskRepository;
    private final AgendaMapper agendaMapper;

    public AgendaDto retrieveAllTasks(UUID personId) {
        AgendaDto dto = new AgendaDto();
        dto.setAgendaTaskDtoList(
                taskRepository.findByPersonEntity_IdOrderByDeadlineAtAsc(personId).stream()
                        .map(agendaMapper::toDto)
                        .toList()
        );
        return dto;
    }
}
