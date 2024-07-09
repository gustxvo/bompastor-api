package com.gustxvo.bompastor_api.api.model.user;

import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.model.user.UserRole;

public record UserProfileDto(String name, String email, UserRole role) {

    public static UserProfileDto fromEntity(User user) {
        return new UserProfileDto(user.getName(), user.getEmail(), user.getRole());
    }
}
