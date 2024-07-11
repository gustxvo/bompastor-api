package com.gustxvo.bompastor_api.api.model.user;

import com.gustxvo.bompastor_api.domain.model.user.User;

import java.util.UUID;

public record UserSummary(UUID id, String name, String email) {

    public static UserSummary fromEntity(User user) {
        return new UserSummary(user.getId(), user.getName(), user.getEmail());
    }
}
