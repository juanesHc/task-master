package com.taskmaster.mapper.notification;

import com.taskmaster.dto.notification.request.RegisterNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personEntity", ignore = true)
    @Mapping(target = "taskEntity", ignore = true)
    @Mapping(target = "notificationMessage", source = "message")
    @Mapping(target = "read", constant = "false")
    @Mapping(target = "sent", constant = "false")
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    NotificationEntity fromRequest(RegisterNotificationRequestDto dto);

    @Mapping(target = "type", source = "notificationType")
    RetrieveNotificationResponseDto toResponse(NotificationEntity entity);
}
