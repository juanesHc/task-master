package com.taskmaster.controller.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.service.task.MarkTaskService;
import com.taskmaster.service.task.RegisterTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final RegisterTaskService registerTaskService;
    private final MarkTaskService markTaskService;

    @PostMapping("/register")
    public ResponseEntity<RegisterTaskResponseDto> postTask(@RequestBody RegisterTaskRequestDto registerTaskRequestDto){
        RegisterTaskResponseDto response=registerTaskService.registerTask(registerTaskRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{taskId}/done")
    public ResponseEntity<MarkTaskResponseDto> markAsDone(@PathVariable UUID taskId) {
        markTaskService. markTaskAsDone(taskId);
        return ResponseEntity.noContent().build();
    }

}
