package com.gustxvo.bompastor_api.api.model.auth;

import com.gustxvo.bompastor_api.domain.model.user.UserRole;

public record RegisterRequest(String name, String email, String password, UserRole role) {
}
