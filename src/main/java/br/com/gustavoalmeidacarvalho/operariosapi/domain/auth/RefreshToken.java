package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;

import java.time.Instant;

public record RefreshToken(Integer id, String token, Instant expirationDate, UserEntity user) {

    public boolean isExpired() {
        return expirationDate.isBefore(Instant.now());
    }

}
