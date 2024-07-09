package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.user.UserDto;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserDto> list() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

}
