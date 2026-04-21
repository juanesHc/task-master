package com.taskmaster.service.notification;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.exception.RegisterNotificationException;
import com.taskmaster.exception.RetrievePeopleException;
import com.taskmaster.mapper.notification.NotificationMapper;
import com.taskmaster.repository.NotificationRepository;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterNotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public NotificationEntity registerNotification(String personId, RegisterNotificationRequestDto request) {
        try {
            PersonEntity person = personRepository.findById(UUID.fromString(personId))
                    .orElseThrow(() -> new RetrievePeopleException("Person not found: " + personId));

            NotificationEntity entity = notificationMapper.fromRequest(request);
            entity.setPersonEntity(person);
            if (request.getTaskId() != null && !request.getTaskId().isBlank()) {
                TaskEntity task = taskRepository.findById(UUID.fromString(request.getTaskId()))
                        .orElse(null);
                entity.setTaskEntity(task);
            }

            NotificationEntity saved = notificationRepository.save(entity);
            log.info("Notification {} registered for person {}", saved.getId(), personId);
            return saved;
        } catch (RetrievePeopleException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to register notification", ex);
            throw new RegisterNotificationException("Could not register notification: " + ex.getMessage());
        }
    }
}
