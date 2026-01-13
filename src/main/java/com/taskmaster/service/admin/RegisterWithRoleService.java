package com.taskmaster.service.admin;

import com.taskmaster.dto.admin.request.RegisterWithRoleRequestDto;
import com.taskmaster.dto.admin.response.RegisterWithRoleResponseDto;
import com.taskmaster.dto.notification.NotificationDto;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.enums.NotificationEnum;
import com.taskmaster.mapper.admin.AdminMapper;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.service.notification.RegisterNotificationService;
import com.taskmaster.service.user.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterWithRoleService {

    private static final Logger log = LoggerFactory.getLogger(RegisterPersonService.class);

    private final PersonRepository personRepository;
    private final AdminMapper adminMapper;
    private final RegisterNotificationService registerNotificationService;

    public RegisterWithRoleResponseDto registerWithRole(RegisterWithRoleRequestDto registerWithRoleRequestDto){
        try {
            PersonEntity personEntity = adminMapper. registerWithRoleRequestDtoToPersonEntity(registerWithRoleRequestDto);
            if(validateEmailUnique(registerWithRoleRequestDto.getEmail())){
                log.warn(registerWithRoleRequestDto.getEmail()+" ya esta en uso");
                throw new RuntimeException("El email ya esta en uso");
            }

            PersonEntity personSaved=personRepository.save(personEntity);
            addNotification(personSaved);


            return new RegisterWithRoleResponseDto("Usuario registrado de forma exitosa");
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

        NotificationDto notificationDto =new NotificationDto();
        notificationDto.setNotificationType(NotificationEnum.REGISTRO);
        notificationDto.setPersonId(String.valueOf(personId));
        notificationDto.setMessage("Hola "+personEntity.getGivenName()+", Te has registrado de forma exitosa");

        registerNotificationService.registerNotification(String.valueOf(personId), notificationDto);
    }

}
