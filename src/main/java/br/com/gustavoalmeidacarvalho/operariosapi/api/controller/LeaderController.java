package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.SectorDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leader")
@RequiredArgsConstructor
public class LeaderController {

    private final SectorService sectorService;
    private final EventService eventService;

    @GetMapping("/sector")
    public ResponseEntity<SectorDto> sector(JwtAuthenticationToken token) {
        UUID leaderId = UUID.fromString(token.getName());
        SectorDto sector = sectorService.findByLeaderId(leaderId).stream()
                .findFirst()
                .map(SectorDto::fromDomain)
                .orElseThrow(() -> new IllegalStateException("Sector with no leader"));

        return ResponseEntity.ok(sector);
    }

    @GetMapping("/events")
    public List<EventDto> events(JwtAuthenticationToken token) {
        UUID leaderId = UUID.fromString(token.getName());

        return eventService.findAllBySectorLeaderId(leaderId).stream()
                .map(EventDto::fromDomain)
                .toList();
    }

}
