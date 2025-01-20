package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.infra.auth.RefreshTokenEntity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshTokenEntity generateRefreshToken(UUID userId);

    Optional<RefreshTokenEntity> findByToken(String refreshToken);

    RefreshTokenEntity invalidateToken(RefreshTokenEntity token);

    Instant expirationDate();
}
