package com.taskmaster.service.notification;

import com.taskmaster.dto.notification.request.RetrieveNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.exception.RegisterNotificationException;
import com.taskmaster.mapper.notification.NotificationMapper;
import com.taskmaster.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveNotificationsService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<RetrieveNotificationResponseDto> retrieveNotifications(RetrieveNotificationRequestDto request, UUID personId) {
        try {
            List<NotificationEntity> entities = (request == null || request.getRead() == null)
                    ? notificationRepository.findByPersonEntity_IdOrderByCreatedAtDesc(personId)
                    : notificationRepository.findByReadAndPersonEntity_Id(request.getRead(), personId);
            return entities.stream().map(notificationMapper::toResponse).toList();
        } catch (Exception ex) {
            log.error("Error retrieving notifications for {}", personId, ex);
            throw new RegisterNotificationException("Could not retrieve notifications");
        }
    }
}
