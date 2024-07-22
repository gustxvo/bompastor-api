package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.auth.LoginRequest;
import com.gustxvo.bompastor_api.api.model.auth.LoginResponse;
import com.gustxvo.bompastor_api.api.model.auth.RegisterRequest;
import com.gustxvo.bompastor_api.api.model.user.UserDto;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final int EXPIRES_IN = 86400 * 30;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .filter(credentials -> isLoginCorrect(loginRequest, credentials))
                .orElseThrow(() -> new BadCredentialsException("User or password is invalid"));

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("bompastor-api")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRES_IN))
                .claim("scope", user.getRole())
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, EXPIRES_IN));
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, User user) {
        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }
}
