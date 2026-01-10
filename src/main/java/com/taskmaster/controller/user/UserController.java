package com.taskmaster.controller.user;

import com.taskmaster.dto.user.request.RegisterPersonRequestDto;
import com.taskmaster.dto.user.response.RegisterPersonResponseDto;
import com.taskmaster.service.user.RegisterPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final RegisterPersonService registerPersonService;

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@RequestBody RegisterPersonRequestDto registerPersonRequestDto){

        RegisterPersonResponseDto response=registerPersonService.registerUser(registerPersonRequestDto);

        return ResponseEntity.status(201).body(response);
    }

}
