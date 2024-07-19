package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.notification.NotificationMessage;
import com.gustxvo.bompastor_api.api.model.event.EventDto;
import com.gustxvo.bompastor_api.api.model.event.EventInput;
import com.gustxvo.bompastor_api.api.service.MessagingService;
import com.gustxvo.bompastor_api.domain.model.event.Event;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.EventRepository;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;
    private final MessagingService messagingService;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId()).orElseThrow();
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));
        Set<UUID> workerIds = workers.stream().map(User::getId).collect(Collectors.toSet());

        Event event = new Event();
        event.setSector(sector);
        event.setWorkers(workers);
        event.setDateTime(eventInput.dateTime());

        String title = "Você foi escalado para servir: " + event.getSector().getName();
        String body = formattedDate(event.getDateTime());
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("icon", "https://imgur.com/vkdveuG.jpeg");
        data.put("body", body);

        NotificationMessage notification = new NotificationMessage(title, body, data);

        messagingService.sendNotification(workerIds, notification);

        EventDto eventCreated = EventDto.fromEntity(eventRepository.save(event));
        return new ResponseEntity<>(eventCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("eventId") Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> ResponseEntity.ok(EventDto.fromEntity(event)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable("eventId") Long eventId, @RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));

        Event event = new Event();
        event.setId(eventId);
        event.setSector(sector);
        event.setWorkers(workers);
        event.setDateTime(eventInput.dateTime());

        EventDto updatedEvent = EventDto.fromEntity(eventRepository.save(event));
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable("eventId") Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String formattedDate(LocalDateTime dateTime) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("EEEE dd/MM 'às' HH:mm");
        return StringUtils.capitalize(parser.format(dateTime));
    }

}
