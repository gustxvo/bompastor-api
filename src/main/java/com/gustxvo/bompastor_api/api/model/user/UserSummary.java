package com.gustxvo.bompastor_api.api.model.user;

import java.util.UUID;

public record UserSummary(UUID id, String name, String email) {
}
