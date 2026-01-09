package com.taskmaster.service.task;

import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkTaskService {

    private final TaskRepository taskRepository;

    public MarkTaskResponseDto markTaskAsDone(UUID taskId){

        MarkTaskResponseDto markTaskResponseDto=new MarkTaskResponseDto();

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró una tarea con el id: " + taskId
                ));

        taskEntity.setDone(true);
        taskRepository.save(taskEntity);

       markTaskResponseDto.setSuccessMessage("Tarea marcada como hecha");

       return markTaskResponseDto;

    }

}
