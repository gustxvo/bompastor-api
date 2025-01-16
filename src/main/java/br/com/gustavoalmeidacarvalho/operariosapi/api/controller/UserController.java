package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.DeviceId;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.UserNotificationTokenRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserProfileDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserNotificationToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserNotificationTokenRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserNotificationTokenRepository tokenRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserDto> list() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(JwtAuthenticationToken token) {
        User user = userRepository.findById(UUID.fromString(token.getName())).orElseThrow();
        return ResponseEntity.ok(UserProfileDto.fromEntity(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserProfileDto> editProfile(@RequestBody UserProfileDto profile, JwtAuthenticationToken token) {
        User user = userRepository.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
        if (!emailIsAvailable(profile.email(), user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        user.setName(profile.name());
        user.setEmail(profile.email());
        return ResponseEntity.ok(UserProfileDto.fromEntity(user));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<UserProfileDto> deleteProfile(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    private boolean emailIsAvailable(String currentEmail, String newEmail) {
        return userRepository.existsByEmail(newEmail) && !Objects.equals(currentEmail, newEmail);
    }

    @PostMapping("/allow-notifications")
    public ResponseEntity<Object> allowNotifications(JwtAuthenticationToken jwtToken, @RequestBody UserNotificationTokenRequest firebaseToken) {
        if (tokenRepository.existsByToken(firebaseToken.token())) {
            return new ResponseEntity<>("Token já cadastrado", HttpStatus.BAD_REQUEST);
        }

        UUID userId = UUID.fromString(jwtToken.getName());

        User user = userRepository.findById(userId).orElseThrow();
        UserNotificationToken userToken = new UserNotificationToken(firebaseToken.token(), user);

        var token = tokenRepository.save(userToken);
        DeviceId device = new DeviceId(token.getDeviceId());
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/block-notifications/{deviceId}")
    public ResponseEntity<Void> allowNotifications(@PathVariable("deviceId") Long deviceId) {
        if (!tokenRepository.existsById(deviceId)) {
            return ResponseEntity.notFound().build();
        }
        tokenRepository.deleteById(deviceId);
        return ResponseEntity.noContent().build();
    }
}
