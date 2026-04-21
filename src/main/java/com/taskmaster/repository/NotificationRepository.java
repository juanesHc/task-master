package com.taskmaster.repository;

import com.taskmaster.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> findByReadAndPersonEntity_Id(boolean read, UUID personId);

    List<NotificationEntity> findByPersonEntity_IdOrderByCreatedAtDesc(UUID personId);
}
