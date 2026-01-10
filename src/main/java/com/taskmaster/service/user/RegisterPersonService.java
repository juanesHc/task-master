package com.taskmaster.service.user;

import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.mapper.user.PersonMapper;
import com.taskmaster.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPersonService {

    private static final Logger log = LoggerFactory.getLogger(RegisterPersonService.class);
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public RegisterPersonResponseDto registerUser(RegisterPersonRequestDto registerPersonRequestDto){
try {
    PersonEntity personEntity = personMapper.registerPersonRequestDtoToPersonEntity(registerPersonRequestDto);
    personRepository.save(personEntity);

    return new RegisterPersonResponseDto("Usuario registrado de forma exitosa");
}catch (Exception exception){
    log.error("ocurrio un error al registrar al usuario "+exception);
    throw new RuntimeException("No se pudo registrar al usuario");
}




    }

}
