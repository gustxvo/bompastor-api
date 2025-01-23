package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.SectorDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.LeaderService;
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

    private final LeaderService leaderService;

    @GetMapping("/sector")
    public ResponseEntity<SectorDto> getLeaderSector(JwtAuthenticationToken token) {
        UUID leaderId = UUID.fromString(token.getName());
        Sector sector = leaderService.findSectorByLeaderId(leaderId);

        return ResponseEntity.ok(SectorDto.fromDomain(sector));
    }

    @GetMapping("/events")
    public List<EventDto> getLeaderEvents(JwtAuthenticationToken token) {
        UUID leaderId = UUID.fromString(token.getName());

        return leaderService.findEventsByLeaderId(leaderId).stream()
                .map(EventDto::fromDomain)
                .toList();
    }

}
