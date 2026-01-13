package com.taskmaster.controller.notification;

import com.taskmaster.dto.notification.request.RetrieveNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.service.notification.MarkNotificationService;
import com.taskmaster.service.notification.RetrieveNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final MarkNotificationService markNotificationService;
    private final RetrieveNotificationsService retrieveNotificationsService;

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<MarkTaskResponseDto> markAsRead(@PathVariable UUID notificationId) {
        markNotificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{personId}/retrieve")
    public ResponseEntity<List<RetrieveNotificationResponseDto>> getNotifications( @RequestParam(required = false) Boolean read,@PathVariable String personId){

        RetrieveNotificationRequestDto requestDto =
                new RetrieveNotificationRequestDto(read);
        List<RetrieveNotificationResponseDto> response=retrieveNotificationsService.retrieveNotifications(requestDto,personId);

        return ResponseEntity.status(201).body(response);
    }
}
