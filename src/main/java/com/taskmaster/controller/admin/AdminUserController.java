package com.taskmaster.controller.admin;

import com.taskmaster.dto.user.response.PersonProfileDto;
import com.taskmaster.service.user.PersonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final PersonQueryService personQueryService;

    @GetMapping
    public ResponseEntity<List<PersonProfileDto>> listAll() {
        return ResponseEntity.ok(personQueryService.findAll());
    }
}
