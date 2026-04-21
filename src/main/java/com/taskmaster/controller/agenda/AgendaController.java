package com.taskmaster.controller.agenda;

import com.taskmaster.dto.agenda.AgendaDto;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.agenda.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class AgendaController {

    private final AgendaService agendaService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    public ResponseEntity<AgendaDto> myAgenda() {
        return ResponseEntity.ok(agendaService.retrieveAllTasks(currentUserService.requireId()));
    }
}
