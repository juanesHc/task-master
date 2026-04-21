package com.taskmaster.service.notification;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import com.taskmaster.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationDispatcher {

    private final RegisterNotificationService registerNotificationService;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Transactional
    public void dispatchReminder(TaskEntity task, NotificationTypeEnum type, String body) {
        PersonEntity person = task.getPersonEntity();

        NotificationEntity inApp = persistReminder(task, person, NotificationChannelEnum.IN_APP, type, body);
        markSent(inApp);

        NotificationEntity mail = persistReminder(task, person, NotificationChannelEnum.EMAIL, type, body);
        emailService.send(person.getEmail(), subjectFor(type, task), body);
        markSent(mail);
    }

    private NotificationEntity persistReminder(TaskEntity task, PersonEntity person,
                                               NotificationChannelEnum channel,
                                               NotificationTypeEnum type, String body) {
        RegisterNotificationRequestDto req = new RegisterNotificationRequestDto();
        req.setPersonId(person.getId().toString());
        req.setTaskId(task.getId().toString());
        req.setNotificationType(type);
        req.setChannel(channel);
        req.setMessage(body);
        req.setScheduledAt(LocalDateTime.now());
        return registerNotificationService.registerNotification(person.getId().toString(), req);
    }

    private void markSent(NotificationEntity entity) {
        entity.setSent(true);
        entity.setSentAt(LocalDateTime.now());
        notificationRepository.save(entity);
    }

    private String subjectFor(NotificationTypeEnum type, TaskEntity task) {
        return switch (type) {
            case REMINDER_ONE_HOUR -> "Reminder: '" + task.getTitle() + "' is due in 1 hour";
            case REMINDER_TEN_MINUTES -> "Reminder: '" + task.getTitle() + "' is due in 10 minutes";
            case OVERDUE -> "Overdue: '" + task.getTitle() + "' is past its deadline";
            default -> "Taskmaster: " + task.getTitle();
        };
    }
}
