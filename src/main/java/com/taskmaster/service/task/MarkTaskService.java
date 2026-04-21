package com.taskmaster.service.task;

import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.TaskExecutionLogEntity;
import com.taskmaster.entity.enums.ExecutionOutcomeEnum;
import com.taskmaster.entity.enums.TaskStatusEnum;
import com.taskmaster.exception.BusinessRuleException;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.repository.TaskExecutionLogRepository;
import com.taskmaster.repository.TaskRepository;
import com.taskmaster.security.AuthenticatedPerson;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.scheduler.ReminderScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkTaskService {

    private final TaskRepository taskRepository;
    private final TaskExecutionLogRepository executionLogRepository;
    private final CronEvaluator cronEvaluator;
    private final ReminderScheduler reminderScheduler;
    private final CurrentUserService currentUserService;

    @Transactional
    public MarkTaskResponseDto markTaskAsDone(UUID taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + taskId));

        AuthenticatedPerson current = currentUserService.require();
        if (!task.getPersonEntity().getId().equals(current.id())) {
            throw new BusinessRuleException("You cannot complete tasks that do not belong to you");
        }

        LocalDateTime now = LocalDateTime.now();
        recordExecution(task, ExecutionOutcomeEnum.COMPLETED, now);
        task.setLastCompletedAt(now);

        if (task.isPeriodic()) {
            resetForNextCycle(task, now);
        } else {
            task.setDone(true);
            task.setStatus(TaskStatusEnum.DONE);
            reminderScheduler.cancel(task.getId());
        }

        taskRepository.save(task);
        log.info("Task {} marked done (type={})", task.getId(), task.getTaskType());
        return new MarkTaskResponseDto("Task marked as done");
    }

    private void resetForNextCycle(TaskEntity task, LocalDateTime now) {
        String cron = task.getPeriodicity().getCronExpression();
        String tz = task.getPeriodicity().getTimezone();
        LocalDateTime nextDeadline = cronEvaluator.nextAfter(cron, tz, now);

        task.setStatus(TaskStatusEnum.PENDING);
        task.setDone(false);
        task.setDeadlineAt(nextDeadline);
        task.setNextRunAt(nextDeadline);

        reminderScheduler.reschedule(task);
        recordExecution(task, ExecutionOutcomeEnum.RESET, now);
    }

    private void recordExecution(TaskEntity task, ExecutionOutcomeEnum outcome, LocalDateTime at) {
        TaskExecutionLogEntity entry = new TaskExecutionLogEntity();
        entry.setTaskEntity(task);
        entry.setPersonEntity(task.getPersonEntity());
        entry.setDeadlineAt(task.getDeadlineAt());
        entry.setRecordedAt(at);
        entry.setOutcome(outcome);
        executionLogRepository.save(entry);
    }
}
