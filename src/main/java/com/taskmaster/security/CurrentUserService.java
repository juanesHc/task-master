package com.taskmaster.security;

import com.taskmaster.exception.AuthenticationFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    public AuthenticatedPerson require() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthenticatedPerson principal)) {
            throw new AuthenticationFailureException("No authenticated principal available");
        }
        return principal;
    }

    public UUID requireId() {
        return require().id();
    }
}
