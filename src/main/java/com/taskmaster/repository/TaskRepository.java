package com.taskmaster.repository;

import com.taskmaster.entity.TaskEntity;
import com.taskmaster.entity.enums.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID>, JpaSpecificationExecutor<TaskEntity> {

    boolean existsByTitleAndPersonEntity_IdAndDoneFalse(String title, UUID personId);

    List<TaskEntity> findByPersonEntity_IdOrderByDeadlineAtAsc(UUID personId);

    List<TaskEntity> findByStatusAndDeadlineAtBefore(TaskStatusEnum status, LocalDateTime moment);

    List<TaskEntity> findByStatus(TaskStatusEnum status);
}
