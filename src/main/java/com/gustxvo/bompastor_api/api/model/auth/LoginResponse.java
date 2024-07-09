package com.gustxvo.bompastor_api.api.model.auth;

public record LoginResponse(String accessToken, int expiresIn) {
}
