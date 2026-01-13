package com.taskmaster.service.notification;

import com.taskmaster.dto.notification.request.RetrieveNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.mapper.notification.NotificationMapper;
import com.taskmaster.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveNotificationsService {

    private static final Logger log = LoggerFactory.getLogger(RetrieveNotificationsService.class);
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<RetrieveNotificationResponseDto> retrieveNotifications(RetrieveNotificationRequestDto retrieveNotificationRequestDto,String personId){
try {
    List<NotificationEntity> entities;

    if (retrieveNotificationRequestDto.getRead() == null) {
        entities = notificationRepository.findByPersonId(UUID.fromString(personId));
    } else {
        entities = notificationRepository.findByReadAndPersonId(retrieveNotificationRequestDto.getRead(), UUID.fromString(personId));
    }

    return adapterNotificationFromEntityToDto(entities);
}catch (Exception exception){
    log.error("Ocurrío un error consultando las notificaciones "+exception);
    throw new RuntimeException("Ocurrío un error consultando las notificaciones");

}
    }

    private List<RetrieveNotificationResponseDto> adapterNotificationFromEntityToDto(List<NotificationEntity> notificationEntityList){
        List<RetrieveNotificationResponseDto> notificationResponseDtoList=new ArrayList<>();

        notificationEntityList.forEach(notificationEntity ->
                notificationResponseDtoList.add(
                        notificationMapper.notificationEntityToRetrieveNotificationResponseDto(notificationEntity)
                ));

        return notificationResponseDtoList;
    }


}
