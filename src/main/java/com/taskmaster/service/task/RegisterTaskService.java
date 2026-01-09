package com.taskmaster.service.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterTaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public RegisterTaskResponseDto registerTask(RegisterTaskRequestDto registerTaskRequestDto){

        TaskEntity taskEntity=taskMapper.taskRequestDtoToTaskEntity(registerTaskRequestDto);
        TaskEntity taskSaved=taskRepository.save(taskEntity);

        RegisterTaskResponseDto registerTaskResponseDto=taskMapper.taskEntityToRegisterTaskResponseDto(taskSaved);
        registerTaskResponseDto.setSuccessMessage("Tarea agregada con exito");

        return registerTaskResponseDto;

    }



}
