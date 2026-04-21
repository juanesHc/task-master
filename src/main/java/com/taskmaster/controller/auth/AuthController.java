package com.taskmaster.controller.auth;

import com.taskmaster.dto.auth.request.GoogleLoginRequestDto;
import com.taskmaster.dto.auth.response.AuthResponseDto;
import com.taskmaster.service.auth.GoogleAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    public ResponseEntity<AuthResponseDto> loginWithGoogle(@Valid @RequestBody GoogleLoginRequestDto request) {
        return ResponseEntity.ok(googleAuthService.loginWithGoogle(request));
    }
}
