package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LoginRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LogoutRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RefreshTokenRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RegisterRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.service.LogoutService;
import br.com.gustavoalmeidacarvalho.operariosapi.api.service.RefreshTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.RefreshToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static br.com.gustavoalmeidacarvalho.operariosapi.config.SecurityConfig.JWT_EXPIRATION_IN_SECONDS;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User user = User.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(registerRequest.role())
                .build();
        UserDto savedUser = UserDto.fromEntity(userRepository.save(user));

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .filter(credentials -> isLoginCorrect(loginRequest, credentials))
                .orElseThrow(() -> new BadCredentialsException("User or password is invalid"));

        String accessToken = generateJwtToken(user);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user.getId());

        return ResponseEntity.ok(new JwtTokenResponseDto(accessToken, refreshToken.getToken(), JWT_EXPIRATION_IN_SECONDS));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.token())
                .map(refreshTokenService::invalidateToken)
                .orElseThrow();

        String accessToken = generateJwtToken(refreshToken.getUser());

        return ResponseEntity.ok(new JwtTokenResponseDto(accessToken, refreshToken.getToken(), JWT_EXPIRATION_IN_SECONDS));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        logoutService.logout(logoutRequest.deviceId(), logoutRequest.refreshToken().toString());
        return ResponseEntity.noContent().build();
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, User user) {
        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }

    private String generateJwtToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("bompastor-api")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_EXPIRATION_IN_SECONDS))
                .claim("scope", user.getRole())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
