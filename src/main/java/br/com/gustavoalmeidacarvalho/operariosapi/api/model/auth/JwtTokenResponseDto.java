package br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth;

public record JwtTokenResponseDto(String accessToken, String refreshToken, int expiresIn) {

    /**
     * 30 days in seconds
     */
    public static int REFRESH_TOKEN_EXPIRATION_IN_SECONDS = 2_592_000;

}
