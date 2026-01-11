package com.taskmaster.controller.agenda;

import com.taskmaster.dto.agenda.AgendaDto;
import com.taskmaster.service.agenda.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping("/retrieve/{personId}")
    public ResponseEntity<AgendaDto> getAgenda(@PathVariable String personId){
        AgendaDto response=agendaService.retrieveAllTasks(personId);
        return ResponseEntity.status(201).body(response);

    }

}
