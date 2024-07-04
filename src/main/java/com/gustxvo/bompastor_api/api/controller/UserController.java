package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    @PostMapping
    public User login(@RequestBody User user) {
        return userRepository.findByEmail(user).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
