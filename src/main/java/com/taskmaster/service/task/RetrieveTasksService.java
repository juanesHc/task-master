package com.taskmaster.service.task;

import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.specification.task.TaskSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RetrieveTasksService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<RetrieveTaskFilterResponseDto> retrieveTasks(
            RetrieveTaskFilterRequestDto filter,String personId
    ) {

        List<RetrieveTaskFilterResponseDto> responseDtos=new ArrayList<>();

        Specification<TaskEntity> spec = Specification
                .where(TaskSpecification.belongsToUser(UUID.fromString(personId)))

                .and(TaskSpecification.buildTaskSpecification(filter));

        List<TaskEntity> filteredTasks = taskRepository.findAll(spec);
        filteredTasks.forEach(taskEntity -> responseDtos.add(taskMapper.taskEntityToRetrieveFilterTaskResponseDto(taskEntity)));

        return responseDtos;
    }
}
