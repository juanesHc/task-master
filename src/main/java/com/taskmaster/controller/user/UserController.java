package com.taskmaster.controller.user;

import com.taskmaster.dto.user.response.PersonProfileDto;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.user.PersonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PersonQueryService personQueryService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PersonProfileDto> me() {
        return ResponseEntity.ok(personQueryService.findById(currentUserService.requireId()));
    }
}
