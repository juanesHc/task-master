package com.taskmaster.controller.notification;

import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.service.notification.MarkNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final MarkNotificationService markNotificationService;

    @PatchMapping("/{notificationId}/viewed")
    public ResponseEntity<MarkTaskResponseDto> markAsViewed(@PathVariable UUID notificationId) {
        markNotificationService.markNotificationAsViewed(notificationId);
        return ResponseEntity.noContent().build();
    }
}
