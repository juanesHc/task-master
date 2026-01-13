package com.taskmaster.service.notification;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.mapper.notification.NotificationMapper;
import com.taskmaster.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNotificationService {

    private static final Logger log = LoggerFactory.getLogger(RegisterNotificationService.class);
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    public void registerNotification(String personId, RegisterNotificationRequestDto registerNotificationRequestDto){
        try {
            NotificationEntity notificationEntity = notificationMapper.notificationDtoToNotificationEntity(registerNotificationRequestDto);

            notificationRepository.save(notificationEntity);
            log.info("Notificación registrada con exito");
        }catch (Exception exception){
            log.error("Ocurrió un error registrando la notificación :"+exception);
            throw new RuntimeException();
        }
    }

}
