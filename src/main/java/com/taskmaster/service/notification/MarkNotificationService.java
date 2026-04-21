package com.taskmaster.service.notification;

import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.exception.BusinessRuleException;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.repository.NotificationRepository;
import com.taskmaster.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarkNotificationService {

    private final NotificationRepository notificationRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public void markNotificationAsRead(UUID notificationId) {
        NotificationEntity entity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + notificationId));
        if (!entity.getPersonEntity().getId().equals(currentUserService.requireId())) {
            throw new BusinessRuleException("Cannot modify a notification you do not own");
        }
        entity.setRead(true);
        notificationRepository.save(entity);
    }
}
