package com.taskmaster.controller.task;

import com.taskmaster.dto.task.request.RegisterTaskRequestDto;
import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.dto.task.response.MarkTaskResponseDto;
import com.taskmaster.dto.task.response.RegisterTaskResponseDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.service.task.MarkTaskService;
import com.taskmaster.service.task.RegisterTaskService;
import com.taskmaster.service.task.RetrieveTasksService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final RegisterTaskService registerTaskService;
    private final MarkTaskService markTaskService;
    private final RetrieveTasksService retrieveTasksService;

    @PostMapping("/{personId}/register")
    public ResponseEntity<RegisterTaskResponseDto> postTask(@PathVariable String personId,@RequestBody RegisterTaskRequestDto registerTaskRequestDto){
        RegisterTaskResponseDto response=registerTaskService.registerTask(personId,registerTaskRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{taskId}/done")
    public ResponseEntity<MarkTaskResponseDto> markAsDone(@PathVariable UUID taskId) {
        markTaskService. markTaskAsDone(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{personId}/retrieve")
    public ResponseEntity<List<RetrieveTaskFilterResponseDto>> getTasks(@PathVariable String personId,RetrieveTaskFilterRequestDto retrieveTaskFilterRequestDto){
       List<RetrieveTaskFilterResponseDto> response= retrieveTasksService.retrieveTasks(retrieveTaskFilterRequestDto,personId);
        return ResponseEntity.status(201).body(response);
    }




}
