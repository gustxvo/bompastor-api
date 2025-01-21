package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.DeviceId;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.UserNotificationTokenRequest;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserProfileDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.notification.UserNotificationToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.notification.UserNotificationTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserService;
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

    private final UserService userService;
    private final UserNotificationTokenService notificationTokenService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserDto> list() {
        return userService.findAll().stream()
                .map(UserDto::fromDomain)
                .toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String userId) {
        User user =
                userService.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(UserDto.fromDomain(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(JwtAuthenticationToken token) {
        User user = userService.findById(UUID.fromString(token.getName())).orElseThrow();
        return ResponseEntity.ok(UserProfileDto.fromEntity(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserProfileDto> editProfile(
            @RequestBody UserProfileDto profile,
            JwtAuthenticationToken token
    ) {
        User user = userService.findById(UUID.fromString(token.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
        if (!emailIsAvailable(profile.email(), user.email())) {
            return ResponseEntity.badRequest().build();
        }

        User updatedUserProfile = new User(user.id(), profile.name(), profile.email(), user.password(), user.role());
        return ResponseEntity.ok(UserProfileDto.fromEntity(updatedUserProfile));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<UserProfileDto> deleteProfile(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        if (!userService.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    private boolean emailIsAvailable(String currentEmail, String newEmail) {
        return userService.existsByEmail(newEmail) && !Objects.equals(currentEmail, newEmail);
    }

    @PostMapping("/allow-notifications")
    public ResponseEntity<Object> allowNotifications(
            JwtAuthenticationToken jwtToken,
            @RequestBody UserNotificationTokenRequest firebaseToken
    ) {
        if (notificationTokenService.existsByToken(firebaseToken.token())) {
            return new ResponseEntity<>("Token já cadastrado", HttpStatus.BAD_REQUEST);
        }

        UUID userId = UUID.fromString(jwtToken.getName());

        User user = userService.findById(userId).orElseThrow();
        UserNotificationToken userToken = UserNotificationToken.create(firebaseToken.token(), user.id());

        var token = notificationTokenService.save(userToken);
        DeviceId device = new DeviceId(token.deviceId());
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/block-notifications/{deviceId}")
    public ResponseEntity<Void> allowNotifications(@PathVariable("deviceId") Long deviceId) {
        if (!notificationTokenService.existsById(deviceId)) {
            return ResponseEntity.notFound().build();
        }
        notificationTokenService.deleteById(deviceId);
        return ResponseEntity.noContent().build();
    }
}
