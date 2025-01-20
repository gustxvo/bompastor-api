package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LoginRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LogoutRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RefreshTokenRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RegisterRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.LogoutService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.auth.RefreshTokenServiceImpl;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.auth.RefreshTokenEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserService;
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

import java.time.Instant;

import static br.com.gustavoalmeidacarvalho.operariosapi.config.SecurityConfig.JWT_EXPIRATION_IN_SECONDS;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        User user = User.create(
                registerRequest.name(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.role()
        );

        UserDto savedUser = UserDto.fromDomain(userService.save(user));

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email())
                .filter(userCredentials -> isLoginCorrect(loginRequest, userCredentials))
                .orElseThrow(() -> new BadCredentialsException("User or password is invalid"));

        String accessToken = generateJwtToken(user);
        RefreshTokenEntity refreshToken = refreshTokenService.generateRefreshToken(user.id());

        return ResponseEntity.ok(new JwtTokenResponseDto(
                accessToken,
                refreshToken.getToken(),
                JWT_EXPIRATION_IN_SECONDS
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(refreshTokenRequest.token())
                .map(refreshTokenService::invalidateToken)
                .orElseThrow();

        String accessToken = generateJwtToken(refreshToken.getUser().toModel());

        return ResponseEntity.ok(new JwtTokenResponseDto(
                accessToken,
                refreshToken.getToken(),
                JWT_EXPIRATION_IN_SECONDS
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        logoutService.logout(logoutRequest.deviceId(), logoutRequest.refreshToken().toString());
        return ResponseEntity.noContent().build();
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, User user) {
        return passwordEncoder.matches(loginRequest.password(), user.password());
    }

    private String generateJwtToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("bompastor-api")
                .subject(user.id().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_EXPIRATION_IN_SECONDS))
                .claim("scope", user.role())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
