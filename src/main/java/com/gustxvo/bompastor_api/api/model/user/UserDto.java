package com.gustxvo.bompastor_api.api.model.user;

import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.model.user.UserRole;

import java.util.UUID;

public record UserDto(UUID id, String name, String email, UserRole role) {

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
