package com.taskmaster.controller.notification;

import com.taskmaster.dto.notification.request.RetrieveNotificationRequestDto;
import com.taskmaster.dto.notification.response.RetrieveNotificationResponseDto;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.notification.MarkNotificationService;
import com.taskmaster.service.notification.RetrieveNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class NotificationController {

    private final MarkNotificationService markNotificationService;
    private final RetrieveNotificationsService retrieveNotificationsService;
    private final CurrentUserService currentUserService;

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markRead(@PathVariable UUID notificationId) {
        markNotificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RetrieveNotificationResponseDto>> list(@RequestParam(required = false) Boolean read) {
        RetrieveNotificationRequestDto request = new RetrieveNotificationRequestDto(read);
        return ResponseEntity.ok(retrieveNotificationsService.retrieveNotifications(request, currentUserService.requireId()));
    }
}
