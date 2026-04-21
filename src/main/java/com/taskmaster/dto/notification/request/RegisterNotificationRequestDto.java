package com.taskmaster.dto.notification.request;

import com.taskmaster.entity.enums.NotificationChannelEnum;
import com.taskmaster.entity.enums.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNotificationRequestDto {

    private String personId;
    private String taskId;
    private NotificationTypeEnum notificationType;
    private NotificationChannelEnum channel = NotificationChannelEnum.IN_APP;
    private String message;
    private LocalDateTime scheduledAt;
}
