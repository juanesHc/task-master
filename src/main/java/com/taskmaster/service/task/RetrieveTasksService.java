package com.taskmaster.service.task;

import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.specification.task.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveTasksService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<RetrieveTaskFilterResponseDto> retrieveTasks(RetrieveTaskFilterRequestDto filter, UUID personId) {
        Specification<TaskEntity> spec = Specification
                .where(TaskSpecification.belongsToUser(personId))
                .and(TaskSpecification.buildTaskSpecification(filter));
        return taskRepository.findAll(spec).stream()
                .map(taskMapper::toFilterResponse)
                .toList();
    }
}
