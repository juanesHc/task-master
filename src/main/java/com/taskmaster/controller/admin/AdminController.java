package com.taskmaster.controller.admin;

import com.taskmaster.dto.admin.request.RegisterWithRoleRequestDto;
import com.taskmaster.dto.admin.response.RegisterWithRoleResponseDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.service.admin.RegisterWithRoleService;
import com.taskmaster.service.user.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final RegisterWithRoleService registerWithRoleService;

    @PostMapping("/register")
    public ResponseEntity<RegisterWithRoleResponseDto> postUser(@RequestBody RegisterWithRoleRequestDto registerWithRoleRequestDto){

        RegisterWithRoleResponseDto response= registerWithRoleService.registerWithRole(registerWithRoleRequestDto);

         return ResponseEntity.status(201).body(response);
    }

}
