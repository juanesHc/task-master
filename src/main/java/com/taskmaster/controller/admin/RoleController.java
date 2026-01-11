package com.taskmaster.controller.admin;

import com.taskmaster.dto.role.RoleDtos;
import com.taskmaster.service.admin.RetrieveRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class RoleController {
    private final RetrieveRoleService retrieveRoleService;

    @GetMapping("/retrieve/role")
    public ResponseEntity<RoleDtos> getRole(){

        RoleDtos response=retrieveRoleService.retrieveRoles();

        return ResponseEntity.status(201).body(response);
    }
}
