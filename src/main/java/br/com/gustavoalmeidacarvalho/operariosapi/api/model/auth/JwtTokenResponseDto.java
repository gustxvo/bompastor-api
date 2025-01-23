package br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth;

import java.util.UUID;

public record JwtTokenResponseDto(String accessToken, UUID refreshToken, int expiresIn) {

    /**
     * 30 days in seconds
     */
    public static int REFRESH_TOKEN_EXPIRATION_IN_SECONDS = 2_592_000;

}
