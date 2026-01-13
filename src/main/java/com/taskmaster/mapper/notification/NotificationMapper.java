package com.taskmaster.mapper.notification;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationMapper {

    public NotificationEntity notificationDtoToNotificationEntity(RegisterNotificationRequestDto registerNotificationRequestDto){

        NotificationEntity notificationEntity=new NotificationEntity();
        PersonEntity personEntity=new PersonEntity();

        personEntity.setId(UUID.fromString(registerNotificationRequestDto.getPersonId()));

        notificationEntity.setNotificationMessage(registerNotificationRequestDto.getMessage());
        notificationEntity.setNotificationType(registerNotificationRequestDto.getNotificationType());
        notificationEntity.setRead(false);
        notificationEntity.setPerson(personEntity);

        return notificationEntity;
    }

    public RetrieveNotificationResponseDto notificationEntityToRetrieveNotificationResponseDto(NotificationEntity notificationEntity){
        return new RetrieveNotificationResponseDto(notificationEntity.getNotificationMessage());
    }

}
