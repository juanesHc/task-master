package com.taskmaster.specification.task;

import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class TaskSpecification {

    private static Specification<TaskEntity> hasTitle(String title){
        return ((root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),"%"+title.toLowerCase()+"%") );
    }

    private static Specification<TaskEntity> isDone(Boolean done) {
        return (root, query, cb) ->
                cb.equal(root.get("done"), done);
    }

    private static Specification<TaskEntity> createdBetween(
            LocalDate createdAt,
            LocalDate expiration
    ) {
        return (root, query, cb) ->
                cb.between(root.get("createdAt"), createdAt, expiration);
    }

    public static Specification<TaskEntity> belongsToUser(UUID personId) {
        return (root, query, cb) ->
                cb.equal(root.get("person").get("id"), personId);
    }




    public static Specification<TaskEntity> buildTaskSpecification(RetrieveTaskFilterRequestDto taskFilterRequestDto){

        Specification<TaskEntity> taskSpec=Specification.where(null);

        if (taskFilterRequestDto.getTitle() != null && !taskFilterRequestDto.getTitle().isBlank()) {
            taskSpec = taskSpec.and(hasTitle(taskFilterRequestDto.getTitle()));
        }

        if (taskFilterRequestDto.getCreatedAt() != null && taskFilterRequestDto.getExpiration() != null) {
            taskSpec = taskSpec.and(
                    createdBetween(
                            taskFilterRequestDto.getCreatedAt(),
                            taskFilterRequestDto.getExpiration()
                    )
            );
        }

        if (taskFilterRequestDto.getDone() != null) {
            taskSpec = taskSpec.and(isDone(taskFilterRequestDto.getDone()));
        }

        return taskSpec;
    }

}
