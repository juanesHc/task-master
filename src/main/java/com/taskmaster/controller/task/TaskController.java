package com.taskmaster.controller.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.task.MarkTaskService;
import com.taskmaster.service.task.RegisterTaskService;
import com.taskmaster.service.task.RetrieveTasksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class TaskController {

    private final RegisterTaskService registerTaskService;
    private final MarkTaskService markTaskService;
    private final RetrieveTasksService retrieveTasksService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public ResponseEntity<RegisterTaskResponseDto> register(@Valid @RequestBody RegisterTaskRequestDto request) {
        UUID personId = currentUserService.requireId();
        RegisterTaskResponseDto response = registerTaskService.registerTask(personId, request);
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{taskId}/done")
    public ResponseEntity<MarkTaskResponseDto> markDone(@PathVariable UUID taskId) {
        return ResponseEntity.ok(markTaskService.markTaskAsDone(taskId));
    }

    @GetMapping
    public ResponseEntity<List<RetrieveTaskFilterResponseDto>> list(RetrieveTaskFilterRequestDto filter) {
        UUID personId = currentUserService.requireId();
        return ResponseEntity.ok(retrieveTasksService.retrieveTasks(filter, personId));
    }
}
