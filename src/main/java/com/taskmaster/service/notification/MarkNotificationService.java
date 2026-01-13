package com.taskmaster.service.notification;

import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.repository.NotificationRepository;
import com.taskmaster.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkNotificationService {

    private final NotificationRepository notificationRepository;

    public void markNotificationAsRead(UUID notificationId){


        NotificationEntity notificationEntity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró una tarea con el id: " + notificationId
                ));

        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);

    }

}
