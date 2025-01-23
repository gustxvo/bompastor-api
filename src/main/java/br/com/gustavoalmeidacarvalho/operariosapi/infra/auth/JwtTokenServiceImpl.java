package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.JwtTokenPair;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.JwtTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.RefreshToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ExpiredRefreshTokenException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.UserService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto.REFRESH_TOKEN_EXPIRATION_IN_SECONDS;
import static br.com.gustavoalmeidacarvalho.operariosapi.config.SecurityConfig.JWT_EXPIRATION_IN_SECONDS;
import static br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.RefreshToken.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.application.name}")
    private String applicationName;

    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtEncoder jwtEncoder;

    @Override
    public JwtTokenPair generateJwtTokenPair(User user) {
        String accessToken = generateAccessToken(user);
        RefreshToken refreshToken = generateRefreshToken(user.id());

        return new JwtTokenPair(accessToken, refreshToken.token());
    }

    @Override
    public JwtTokenPair invalidateRefreshToken(UUID token) {
        RefreshToken refreshToken = findByToken(token);
        User user = userService.findById(refreshToken.user().id());
        clearRefreshToken(token);
        return generateJwtTokenPair(user);
    }

    @Override
    public void clearRefreshToken(UUID refreshToken) {
        RefreshToken token = findByToken(refreshToken);
        refreshTokenRepository.deleteById(token.id());
    }

    private RefreshToken findByToken(UUID token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token.toString())
                .orElseThrow(() -> new ResourceNotFoundException(REFRESH_TOKEN, token))
                .toModel();

        if (refreshToken.isExpired()) {
            throw new ExpiredRefreshTokenException(token);
        }
        return refreshToken;
    }


    private String generateAccessToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .subject(user.id().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_EXPIRATION_IN_SECONDS))
                .claim("scope", user.role())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public RefreshToken generateRefreshToken(UUID userId) {
        User user = userService.findById(userId);
        Instant expirationDate = Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(new UserEntity(user))
                .token(UUID.randomUUID().toString())
                .expirationDate(expirationDate)
                .build();

        return refreshTokenRepository.save(refreshToken).toModel();
    }

}
