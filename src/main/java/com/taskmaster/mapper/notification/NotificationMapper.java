package com.taskmaster.mapper.notification;

import com.taskmaster.dto.notification.NotificationDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationMapper {

    public NotificationEntity notificationDtoToNotificationEntity(NotificationDto notificationDto){

        NotificationEntity notificationEntity=new NotificationEntity();
        PersonEntity personEntity=new PersonEntity();

        personEntity.setId(UUID.fromString(notificationDto.getPersonId()));

        notificationEntity.setNotificationMessage(notificationDto.getMessage());
        notificationEntity.setNotificationType(notificationDto.getNotificationType());
        notificationEntity.setReaded(false);
        notificationEntity.setPerson(personEntity);

        return notificationEntity;
    }

}
