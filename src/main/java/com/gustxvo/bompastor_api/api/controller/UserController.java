package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.mapper.UserMapper;
import com.gustxvo.bompastor_api.api.model.user.LoginInput;
import com.gustxvo.bompastor_api.api.model.user.RegisterUserInput;
import com.gustxvo.bompastor_api.api.model.user.UserDto;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public List<UserDto> list() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody RegisterUserInput registerUserInput) {
        User user = userRepository.save(registerUserInput.toEntity());
        return UserDto.fromEntity(user);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginInput loginInput) {
        User user = userRepository.findByEmail(loginInput.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if (Objects.equals(loginInput.password(), user.getPassword())) {
            return ResponseEntity.ok(userMapper.toModel(user));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
