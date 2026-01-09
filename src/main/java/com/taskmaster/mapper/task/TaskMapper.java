package com.taskmaster.mapper.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskEntity taskRequestDtoToTaskEntity(RegisterTaskRequestDto registerTaskRequestDto){
        TaskEntity taskEntity=new TaskEntity();
        PersonEntity personEntity=new PersonEntity();

        taskEntity.setDescription(registerTaskRequestDto.getDescription());
        taskEntity.setTitle(registerTaskRequestDto.getTitle());
        taskEntity.setExpirationTime(registerTaskRequestDto.getExpirationTime());
        taskEntity.setExpirationDate(registerTaskRequestDto.getExpirationDate());
        taskEntity.setDone(false);

        personEntity.setId(registerTaskRequestDto.getPersonId());
        taskEntity.setPerson(personEntity);

        return taskEntity;
    }

    public RegisterTaskResponseDto taskEntityToRegisterTaskResponseDto(TaskEntity taskEntity){
        RegisterTaskResponseDto registerTaskResponseDto=new RegisterTaskResponseDto();

        registerTaskResponseDto.setTimeLeft(LocalDateTime.of(taskEntity.getExpirationDate(),taskEntity.getExpirationTime()));
        return registerTaskResponseDto;
    }

}
