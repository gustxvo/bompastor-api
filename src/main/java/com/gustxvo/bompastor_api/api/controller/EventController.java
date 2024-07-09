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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;

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

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> update(
            @PathVariable("eventId") Long eventId, @RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId()).orElseThrow();
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));

        Event event = new Event();
        event.setId(eventId);
        event.setSector(sector);
        event.setWorkers(workers);
        event.setDateTime(eventInput.dateTime());

        EventDto eventCreated = EventDto.fromEntity(eventRepository.save(event));
        return ResponseEntity.ok(eventCreated);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable("eventId") Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
