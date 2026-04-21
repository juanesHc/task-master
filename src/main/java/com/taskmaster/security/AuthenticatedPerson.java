package com.taskmaster.security;

import com.taskmaster.entity.enums.RoleEnum;

import java.util.UUID;

public record AuthenticatedPerson(UUID id, String email, RoleEnum role) {
}
