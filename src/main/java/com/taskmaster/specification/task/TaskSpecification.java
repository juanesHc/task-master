package com.taskmaster.specification.task;

import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TaskSpecification {

    private TaskSpecification() {
    }

    public static Specification<TaskEntity> belongsToUser(UUID personId) {
        return (root, query, cb) -> cb.equal(root.get("personEntity").get("id"), personId);
    }

    public static Specification<TaskEntity> buildTaskSpecification(RetrieveTaskFilterRequestDto filter) {
        Specification<TaskEntity> spec = Specification.where(null);
        if (filter == null) {
            return spec;
        }
        if (filter.getTitle() != null && !filter.getTitle().isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
        }
        if (filter.getDone() != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("done"), filter.getDone()));
        }
        if (filter.getStatus() != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }
        if (filter.getTaskType() != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("taskType"), filter.getTaskType()));
        }
        if (filter.getCreatedAt() != null) {
            spec = spec.and((root, q, cb) -> cb.between(
                    root.get("createdAt"),
                    filter.getCreatedAt().atStartOfDay(),
                    filter.getCreatedAt().atTime(23, 59, 59)));
        }
        if (filter.getDeadlineOn() != null) {
            spec = spec.and((root, q, cb) -> cb.between(
                    root.get("deadlineAt"),
                    filter.getDeadlineOn().atStartOfDay(),
                    filter.getDeadlineOn().atTime(23, 59, 59)));
        }
        return spec;
    }
}
