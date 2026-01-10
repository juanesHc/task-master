package com.taskmaster.service.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterTaskService {

    private static final Logger log = LoggerFactory.getLogger(RegisterTaskService.class);
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public RegisterTaskResponseDto registerTask(String personId,RegisterTaskRequestDto registerTaskRequestDto){
try {

    if(validateTitle(registerTaskRequestDto.getTitle(),personId)){
        log.warn("No pueden existir 2 tareas pendientes con el mismo nombre");
        throw new RuntimeException("No pueden existir 2 tareas pendientes con el mismo nombre");
    }

    TaskEntity taskEntity = taskMapper.taskRequestDtoToTaskEntity(registerTaskRequestDto,personId);
    TaskEntity taskSaved = taskRepository.save(taskEntity);

    RegisterTaskResponseDto registerTaskResponseDto = taskMapper.taskEntityToRegisterTaskResponseDto(taskSaved);
    registerTaskResponseDto.setSuccessMessage("Tarea agregada con exito");
    log.info("Se agregó la tarea");

    return registerTaskResponseDto;
}catch(Exception exception){

    log.error("No se pudo agregar la tarea");
    throw new RuntimeException("No se pudo agregar la tarea "+exception);

}
    }

    private boolean validateTitle(String title, String personId) {
       return taskRepository.existsByTitleAndPersonIdAndDoneFalse(title,UUID.fromString(personId));


    }




}
