package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.event.EventDto;
import com.gustxvo.bompastor_api.api.model.event.EventInput;
import com.gustxvo.bompastor_api.domain.model.event.Event;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.EventRepository;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<EventDto> list(@Param("userId") String userId) {
        return eventRepository.findAll().stream()
                .filter((event -> event.getWorkers().stream().anyMatch(user -> Objects.equals(user.getId(), UUID.fromString(userId)))))
                .map(EventDto::fromEntity)
                .toList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> createEvent(@RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId()).orElseThrow();
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));

        Event event = new Event();
        event.setSector(sector);
        event.setWorkers(workers);
        event.setDateTime(eventInput.dateTime());

        EventDto eventCreated = EventDto.fromEntity(eventRepository.save(event));
        return ResponseEntity.ok(eventCreated);
    }

    @PostMapping
    public EventDto create() {
        Event event = new Event();
        event.setDateTime(LocalDateTime.now());
        event.setWorkers(Set.of(new User(UUID.fromString("00a7b810-9dad-11d1-80b4-00c04fd430c8"))));
        event.setSector(new Sector(1));
        var created = eventRepository.save(event);
        return EventDto.fromEntity(created);
    }
}
