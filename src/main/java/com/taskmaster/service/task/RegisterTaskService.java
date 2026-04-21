package com.taskmaster.service.task;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.entity.PeriodicityEntity;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.entity.enums.TaskTypeEnum;
import com.taskmaster.exception.BusinessRuleException;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.mapper.task.TaskMapper;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.service.notification.RegisterNotificationService;
import com.taskmaster.service.scheduler.ReminderScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterTaskService {

    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CronEvaluator cronEvaluator;
    private final RegisterNotificationService registerNotificationService;
    private final ReminderScheduler reminderScheduler;

    @Transactional
    public RegisterTaskResponseDto registerTask(UUID personId, RegisterTaskRequestDto request) {
        PersonEntity person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + personId));

        if (taskRepository.existsByTitleAndPersonEntity_IdAndDoneFalse(request.getTitle(), personId)) {
            throw new BusinessRuleException("An open task with the same title already exists");
        }

        TaskEntity task = taskMapper.fromRegisterRequest(request);
        task.setPersonEntity(person);
        task.setStatus(TaskStatusEnum.PENDING);

        if (task.getTaskType() == TaskTypeEnum.PERIODIC) {
            requireCron(request);
            cronEvaluator.validate(request.getCronExpression(), request.getTimezone());

            PeriodicityEntity periodicity = new PeriodicityEntity();
            periodicity.setCronExpression(request.getCronExpression());
            periodicity.setTimezone(request.getTimezone() == null ? "UTC" : request.getTimezone());
            periodicity.setTaskEntity(task);
            task.setPeriodicity(periodicity);

            task.setNextRunAt(cronEvaluator.nextAfter(
                    request.getCronExpression(), request.getTimezone(), task.getDeadlineAt()));
        }

        TaskEntity saved = taskRepository.save(task);

        RegisterNotificationRequestDto welcome = new RegisterNotificationRequestDto();
        welcome.setPersonId(personId.toString());
        welcome.setTaskId(saved.getId().toString());
        welcome.setNotificationType(NotificationTypeEnum.TASK_CREATED);
        welcome.setChannel(NotificationChannelEnum.IN_APP);
        welcome.setMessage("Task '" + saved.getTitle() + "' scheduled for " + saved.getDeadlineAt());
        registerNotificationService.registerNotification(personId.toString(), welcome);

        reminderScheduler.scheduleFor(saved);

        RegisterTaskResponseDto response = taskMapper.toRegisterResponse(saved);
        response.setSuccessMessage("Task registered successfully");
        log.info("Task {} registered for person {}", saved.getId(), personId);
        return response;
    }

    private void requireCron(RegisterTaskRequestDto request) {
        if (request.getCronExpression() == null || request.getCronExpression().isBlank()) {
            throw new BusinessRuleException("Periodic tasks require a cronExpression");
        }
    }
}
