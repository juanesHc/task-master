package com.taskmaster.service.scheduler;

import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.TaskExecutionLogEntity;
import com.taskmaster.entity.enums.ExecutionOutcomeEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.repository.TaskExecutionLogRepository;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.service.notification.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueSweepJob {

    private final TaskRepository taskRepository;
    private final TaskExecutionLogRepository executionLogRepository;
    private final NotificationDispatcher dispatcher;

    @Scheduled(cron = "${app.reminder.overdue-sweep-cron}")
    @Transactional
    public void sweep() {
        LocalDateTime now = LocalDateTime.now();
        List<TaskEntity> expired = taskRepository.findByStatusAndDeadlineAtBefore(TaskStatusEnum.PENDING, now);
        if (expired.isEmpty()) {
            return;
        }
        for (TaskEntity task : expired) {
            task.setStatus(TaskStatusEnum.EXPIRED);
            taskRepository.save(task);

            TaskExecutionLogEntity entry = new TaskExecutionLogEntity();
            entry.setTaskEntity(task);
            entry.setPersonEntity(task.getPersonEntity());
            entry.setDeadlineAt(task.getDeadlineAt());
            entry.setRecordedAt(now);
            entry.setOutcome(ExecutionOutcomeEnum.MISSED);
            executionLogRepository.save(entry);

            dispatcher.dispatchReminder(task, NotificationTypeEnum.OVERDUE,
                    "Task '" + task.getTitle() + "' became overdue (deadline " + task.getDeadlineAt() + ").");
        }
        log.info("Swept {} overdue tasks", expired.size());
    }
}
