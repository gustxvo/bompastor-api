package com.gustxvo.bompastor_api.api.model.event;

import com.gustxvo.bompastor_api.domain.model.user.User;

import java.util.UUID;

public record WorkerSummary(UUID id, String name) {

    public static WorkerSummary fromEntity(User user) {
        return new WorkerSummary(user.getId(), user.getName());
    }
}
