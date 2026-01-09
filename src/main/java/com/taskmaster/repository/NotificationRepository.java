package com.taskmaster.repository;

import com.taskmaster.entity.NotificationEntity;
import com.taskmaster.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Notification;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository< NotificationEntity,UUID> {



}
