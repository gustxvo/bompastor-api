package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import java.util.UUID;

public record JwtTokenPair(String accessToken, UUID refreshToken) {
}
