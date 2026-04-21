package com.taskmaster.repository;

import com.taskmaster.entity.TaskExecutionLogEntity;
import com.taskmaster.entity.enums.ExecutionOutcomeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TaskExecutionLogRepository extends JpaRepository<TaskExecutionLogEntity, UUID> {

    @Query("SELECT COUNT(l) FROM TaskExecutionLogEntity l " +
            "WHERE l.personEntity.id = :personId " +
            "AND l.recordedAt >= :from AND l.recordedAt < :to")
    long countByPersonAndWindow(@Param("personId") UUID personId,
                                @Param("from") LocalDateTime from,
                                @Param("to") LocalDateTime to);

    @Query("SELECT COUNT(l) FROM TaskExecutionLogEntity l " +
            "WHERE l.personEntity.id = :personId " +
            "AND l.outcome = :outcome " +
            "AND l.recordedAt >= :from AND l.recordedAt < :to")
    long countByPersonOutcomeAndWindow(@Param("personId") UUID personId,
                                       @Param("outcome") ExecutionOutcomeEnum outcome,
                                       @Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to);
}
