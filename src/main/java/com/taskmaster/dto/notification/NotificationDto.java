package com.taskmaster.dto.notification;

import com.taskmaster.entity.enums.NotificationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String personId;
    private NotificationEnum notificationType;
    private String message;
}
