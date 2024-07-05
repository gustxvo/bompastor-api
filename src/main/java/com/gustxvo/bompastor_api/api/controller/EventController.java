package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.event.EventDto;
import com.gustxvo.bompastor_api.domain.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @GetMapping
    public List<EventDto> list(@Param("userId") String userId) {
        return eventRepository.findAll().stream()
                .filter((event -> event.getUsers().stream().anyMatch(user -> Objects.equals(user.getId(), UUID.fromString(userId)))))
                .map(EventDto::fromEntity)
                .toList();
    }
}
