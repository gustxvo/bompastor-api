package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.RefreshTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto.REFRESH_TOKEN_EXPIRATION_IN_SECONDS;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public RefreshTokenEntity generateRefreshToken(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationDate(expirationDate())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshTokenEntity invalidateToken(RefreshTokenEntity token) {
        refreshTokenRepository.deleteById(token.getId());
        if (token.isExpired()) {
            throw new IllegalStateException(token.getToken() + " Refresh token expired. Please log in again");
        }
        return generateRefreshToken(token.getUser().getId());
    }

    @Override
    public Instant expirationDate() {
        return Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);
    }

}
