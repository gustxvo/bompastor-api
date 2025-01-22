package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(Integer id, UUID token, Instant expirationDate, User user) {

    public boolean isExpired() {
        return expirationDate.isBefore(Instant.now());
    }

}
