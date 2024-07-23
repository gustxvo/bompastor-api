package com.gustxvo.bompastor_api.api.service;

import com.gustxvo.bompastor_api.domain.model.user.RefreshToken;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.RefreshTokenRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.gustxvo.bompastor_api.api.model.auth.JwtTokenResponseDto.REFRESH_TOKEN_EXPIRATION_IN_SECONDS;

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

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.deleteById(token.getId());
        }
        return token;
    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);
    }

}
