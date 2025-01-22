package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.JwtTokenResponseDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LoginRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.LogoutRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RefreshTokenRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth.RegisterRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.AuthService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.JwtTokenPair;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.JwtTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static br.com.gustavoalmeidacarvalho.operariosapi.config.SecurityConfig.JWT_EXPIRATION_IN_SECONDS;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        User savedUser = authService.register(registerRequest.toDomain());

        return new ResponseEntity<>(UserDto.fromDomain(savedUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login(@RequestBody LoginRequest loginRequest) {
        User user = authService.loginWithEmailAndPassword(loginRequest.email(), loginRequest.password());
        JwtTokenPair jwtTokenPair = jwtTokenService.generateJwtTokenPair(user);

        return ResponseEntity.ok(new JwtTokenResponseDto(
                jwtTokenPair.accessToken(),
                jwtTokenPair.refreshToken().toString(),
                JWT_EXPIRATION_IN_SECONDS
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        UUID refreshToken = UUID.fromString(refreshTokenRequest.token());
        JwtTokenPair jwtTokenPair = jwtTokenService.invalidateRefreshToken(refreshToken);

        return ResponseEntity.ok(new JwtTokenResponseDto(
                jwtTokenPair.accessToken(),
                jwtTokenPair.refreshToken().toString(),
                JWT_EXPIRATION_IN_SECONDS
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest.deviceId(), logoutRequest.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
