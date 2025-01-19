package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.NotificationMessage;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventInput;
import br.com.gustavoalmeidacarvalho.operariosapi.api.service.MessagingService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.SectorRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserRepository;
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

    private final EventService eventService;
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;
    private final MessagingService messagingService;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId()).orElseThrow();
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));
        Set<UUID> workerIds = workers.stream().map(User::getId).collect(Collectors.toSet());

        Event event = Event.create(eventInput.dateTime(), sector, workers);

        String title = "Você foi escalado para servir: " + event.sector().getName();
        String body = formattedDate(event.dateTime());
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("icon", "https://imgur.com/vkdveuG.jpeg");
        data.put("body", body);

//        NotificationMessage notification = new NotificationMessage(title, body, data);

//        messagingService.sendNotification(workerIds, notification);

        EventDto eventCreated = EventDto.fromDomain(eventService.create(event));
        return new ResponseEntity<>(eventCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("eventId") Long eventId) {
        return eventService.findById(eventId)
                .map(event -> ResponseEntity.ok(EventDto.fromDomain(event)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable("eventId") Long eventId, @RequestBody EventInput eventInput) {
        Sector sector = sectorRepository.findById(eventInput.sectorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Set<User> workers = new HashSet<>(userRepository.findAllById(eventInput.workers()));

        Event event = new Event(eventId, eventInput.dateTime(), sector, workers);

        EventDto updatedEvent = EventDto.fromDomain(eventService.create(event));
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable("eventId") Long eventId) {
        if (!eventService.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String formattedDate(LocalDateTime dateTime) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("EEEE dd/MM 'às' HH:mm");
        return StringUtils.capitalize(parser.format(dateTime));
    }

}
