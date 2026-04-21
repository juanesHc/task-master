package com.taskmaster.service.scheduler;

import com.taskmaster.config.ReminderProperties;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.service.notification.NotificationDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderSchedulerImpl implements ReminderScheduler {

    private final ThreadPoolTaskScheduler scheduler;
    private final ReminderProperties reminderProperties;
    private final TaskRepository taskRepository;
    private final NotificationDispatcher dispatcher;

    private final Map<UUID, List<ScheduledFuture<?>>> scheduledByTask = new ConcurrentHashMap<>();

    @EventListener(ContextRefreshedEvent.class)
    @Transactional(readOnly = true)
    public void rehydrate() {
        List<TaskEntity> pending = taskRepository.findByStatus(TaskStatusEnum.PENDING);
        for (TaskEntity task : pending) {
            scheduleFor(task);
        }
        log.info("Rehydrated {} pending task reminders", pending.size());
    }

    @Override
    public void scheduleFor(TaskEntity task) {
        cancel(task.getId());
        if (task.getDeadlineAt() == null || task.getStatus() != TaskStatusEnum.PENDING) {
            return;
        }
        List<ScheduledFuture<?>> futures = new CopyOnWriteArrayList<>();

        scheduleReminder(task, task.getDeadlineAt().minus(reminderProperties.getLeadOneHour()),
                NotificationTypeEnum.REMINDER_ONE_HOUR, futures);
        scheduleReminder(task, task.getDeadlineAt().minus(reminderProperties.getLeadTenMinutes()),
                NotificationTypeEnum.REMINDER_TEN_MINUTES, futures);
        scheduleReminder(task, task.getDeadlineAt(),
                NotificationTypeEnum.OVERDUE, futures);

        scheduledByTask.put(task.getId(), futures);
        log.debug("Scheduled {} reminders for task {}", futures.size(), task.getId());
    }

    @Override
    public void cancel(UUID taskId) {
        List<ScheduledFuture<?>> futures = scheduledByTask.remove(taskId);
        if (futures != null) {
            futures.forEach(f -> f.cancel(false));
        }
    }

    private void scheduleReminder(TaskEntity task, LocalDateTime when,
                                  NotificationTypeEnum type,
                                  List<ScheduledFuture<?>> bucket) {
        if (when == null || when.isBefore(LocalDateTime.now())) {
            return;
        }
        UUID taskId = task.getId();
        String title = task.getTitle();
        LocalDateTime deadline = task.getDeadlineAt();

        ScheduledFuture<?> future = scheduler.schedule(
                () -> fireReminder(taskId, title, deadline, type),
                when.atZone(ZoneId.systemDefault()).toInstant());
        bucket.add(future);
    }

    private void fireReminder(UUID taskId, String title, LocalDateTime deadline, NotificationTypeEnum type) {
        try {
            TaskEntity current = taskRepository.findById(taskId).orElse(null);
            if (current == null || current.getStatus() != TaskStatusEnum.PENDING) {
                return;
            }
            if (type == NotificationTypeEnum.OVERDUE && current.isDone()) {
                return;
            }
            String body = buildBody(type, title, deadline);
            dispatcher.dispatchReminder(current, type, body);

            if (type == NotificationTypeEnum.OVERDUE) {
                current.setStatus(TaskStatusEnum.EXPIRED);
                taskRepository.save(current);
            }
        } catch (Exception ex) {
            log.error("Failed to fire reminder {} for task {}", type, taskId, ex);
        }
    }

    private String buildBody(NotificationTypeEnum type, String title, LocalDateTime deadline) {
        Duration lead = switch (type) {
            case REMINDER_ONE_HOUR -> reminderProperties.getLeadOneHour();
            case REMINDER_TEN_MINUTES -> reminderProperties.getLeadTenMinutes();
            default -> Duration.ZERO;
        };
        return switch (type) {
            case REMINDER_ONE_HOUR, REMINDER_TEN_MINUTES ->
                    "Heads up — '" + title + "' is due at " + deadline + " (" + formatLead(lead) + " remaining).";
            case OVERDUE -> "Task '" + title + "' is now overdue (deadline was " + deadline + ").";
            default -> title;
        };
    }

    private String formatLead(Duration duration) {
        long minutes = duration.toMinutes();
        return minutes >= 60 ? (minutes / 60) + "h" : minutes + "min";
    }
}
