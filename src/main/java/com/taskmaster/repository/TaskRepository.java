package com.taskmaster.repository;

import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,UUID> , JpaSpecificationExecutor<TaskEntity> {

    boolean existsByTitleAndPersonIdAndDoneFalse(String title, UUID personId);

    List<TaskEntity> findByPersonIdOrderByExpirationDateAscExpirationTimeAsc(UUID personId);
}
