package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(Integer id, UUID token, Instant expirationDate, User user) {

    public static final String REFRESH_TOKEN = "refresh_token";

    public boolean isExpired() {
        return expirationDate.isBefore(Instant.now());
    }

}
