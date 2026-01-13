package com.taskmaster.service.user;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.enums.NotificationEnum;
import com.taskmaster.mapper.user.PersonMapper;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.service.notification.RegisterNotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterPersonService {

    private static final Logger log = LoggerFactory.getLogger(RegisterPersonService.class);

    private final RegisterNotificationService registerNotificationService;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public RegisterPersonResponseDto registerUser(RegisterPersonRequestDto registerPersonRequestDto){
try {
    PersonEntity personEntity = personMapper.registerPersonRequestDtoToPersonEntity(registerPersonRequestDto);
    if(validateEmailUnique(registerPersonRequestDto.getEmail())){
        log.warn(registerPersonRequestDto.getEmail()+" ya esta en uso");
        throw new RuntimeException("El email ya esta en uso");
    }
    PersonEntity personSaved=personRepository.save(personEntity);
    addNotification(personSaved);

    return new RegisterPersonResponseDto("Usuario registrado de forma exitosa");
}catch (Exception exception){
    log.error("ocurrio un error al registrar al usuario "+exception);
    throw new RuntimeException("No se pudo registrar al usuario");
}
    }

    private boolean validateEmailUnique(String email){
        return personRepository.existsByEmail(email);
    }

    private void addNotification(PersonEntity personEntity){
        UUID personId=personRepository.findIdByEmail(personEntity.getEmail());

        RegisterNotificationRequestDto registerNotificationRequestDto =new RegisterNotificationRequestDto();
        registerNotificationRequestDto.setNotificationType(NotificationEnum.REGISTRO);
        registerNotificationRequestDto.setPersonId(String.valueOf(personId));
        registerNotificationRequestDto.setMessage("Hola "+personEntity.getGivenName()+", Te has registrado de forma exitosa");

        registerNotificationService.registerNotification(String.valueOf(personId), registerNotificationRequestDto);
    }

}
