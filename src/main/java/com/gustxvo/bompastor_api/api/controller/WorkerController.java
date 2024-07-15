package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.event.EventSummary;
import com.gustxvo.bompastor_api.api.model.user.WorkerNames;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.model.user.UserRole;
import com.gustxvo.bompastor_api.domain.repository.EventRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @GetMapping
    public WorkerNames workers() {
        var workers = userRepository.findByRoleNot(UserRole.ADMIN);
        return WorkerNames.fromUserList(workers);
    }

    @GetMapping("/events")
    public List<EventSummary> list(JwtAuthenticationToken token) {
        UUID workerId = UUID.fromString(token.getName());

        return eventRepository
                .findAllByWorkers_Id(workerId)
                .stream()
                .map(EventSummary::fromEntity)
                .toList();
    }
}
