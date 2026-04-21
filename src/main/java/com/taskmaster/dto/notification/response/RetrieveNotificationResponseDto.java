package com.taskmaster.dto.notification.response;

import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveNotificationResponseDto {
    private UUID id;
    private String notificationMessage;
    private NotificationTypeEnum type;
    private NotificationChannelEnum channel;
    private boolean read;
    private boolean sent;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
}
