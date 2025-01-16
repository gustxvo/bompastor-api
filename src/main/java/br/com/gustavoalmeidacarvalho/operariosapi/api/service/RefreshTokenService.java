package br.com.gustavoalmeidacarvalho.operariosapi.api.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.RefreshToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.RefreshTokenRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto.REFRESH_TOKEN_EXPIRATION_IN_SECONDS;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken generateRefreshToken(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationDate(expirationDate())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    public RefreshToken invalidateToken(RefreshToken token) {
        refreshTokenRepository.deleteById(token.getId());
        if (token.isExpired()) {
            throw new IllegalStateException(token.getToken() + " Refresh token expired. Please log in again");
        }
        return generateRefreshToken(token.getUser().getId());
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);
    }

}
