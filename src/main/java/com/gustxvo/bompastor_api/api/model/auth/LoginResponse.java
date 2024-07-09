package com.gustxvo.bompastor_api.api.model.auth;

public record LoginResponse(String jwtValue, int expiresIn) {
}
