package com.taskmaster.service.scheduler;

import com.taskmaster.entity.TaskEntity;

import java.util.UUID;

public interface ReminderScheduler {

    void scheduleFor(TaskEntity task);

    void cancel(UUID taskId);

    default void reschedule(TaskEntity task) {
        cancel(task.getId());
        scheduleFor(task);
    }
}
