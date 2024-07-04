package com.gustxvo.bompastor_api.api.model.user;

import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.model.user.UserRole;

public record RegisterUserInput(String name, String email, String password, UserRole role) {

    public User toEntity() {
        return new User(name, email, password, role);
    }
}
