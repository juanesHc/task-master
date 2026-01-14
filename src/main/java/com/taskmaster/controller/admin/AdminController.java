package com.taskmaster.controller.admin;

import com.taskmaster.dto.admin.request.RegisterWithRoleRequestDto;
import com.taskmaster.dto.admin.request.RetrieveUsersFilterRequestDto;
import com.taskmaster.dto.admin.response.RegisterWithRoleResponseDto;
import com.taskmaster.dto.admin.response.RetrieveUsersFilterResponseDto;
import com.taskmaster.dto.task.request.RetrieveTaskFilterRequestDto;
import com.taskmaster.dto.task.response.RetrieveTaskFilterResponseDto;
import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.service.admin.RegisterWithRoleService;
import com.taskmaster.service.admin.RetrieveUsersService;
import com.taskmaster.service.user.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final RegisterWithRoleService registerWithRoleService;
    private final RetrieveUsersService retrieveUsersService;

    @PostMapping("/register")
    public ResponseEntity<RegisterWithRoleResponseDto> postUser(@RequestBody RegisterWithRoleRequestDto registerWithRoleRequestDto){

        RegisterWithRoleResponseDto response= registerWithRoleService.registerWithRole(registerWithRoleRequestDto);

         return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/retrieve")
    public ResponseEntity<List<RetrieveUsersFilterResponseDto>> getTasks(RetrieveUsersFilterRequestDto retrieveUsersFilterRequestDto){
        List<RetrieveUsersFilterResponseDto> response= retrieveUsersService.retrieveUsers(retrieveUsersFilterRequestDto);
        return ResponseEntity.status(201).body(response);
    }

}
