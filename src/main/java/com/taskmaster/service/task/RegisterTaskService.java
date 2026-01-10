package com.taskmaster.service.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterTaskService {

    private static final Logger log = LoggerFactory.getLogger(RegisterTaskService.class);
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public RegisterTaskResponseDto registerTask(RegisterTaskRequestDto registerTaskRequestDto){
try {
    TaskEntity taskEntity = taskMapper.taskRequestDtoToTaskEntity(registerTaskRequestDto);
    TaskEntity taskSaved = taskRepository.save(taskEntity);

    RegisterTaskResponseDto registerTaskResponseDto = taskMapper.taskEntityToRegisterTaskResponseDto(taskSaved);
    registerTaskResponseDto.setSuccessMessage("Tarea agregada con exito");
    log.error("Se agregó la tarea");

    return registerTaskResponseDto;
}catch(Exception exception){

    log.error("No se pudo agregar la tarea");
    throw new RuntimeException("No se pudo agregar la tarea "+exception);

}
    }



}
