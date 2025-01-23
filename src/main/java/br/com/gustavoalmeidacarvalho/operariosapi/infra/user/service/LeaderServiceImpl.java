package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.LeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaderServiceImpl implements LeaderService {

    private final SectorRepository sectorRepository;
    private final EventRepository eventRepository;

    @Override
    public Sector findSectorByLeaderId(UUID leaderId) {
        return sectorRepository.findByLeaderId(leaderId).stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Event> findEventsByLeaderId(UUID leaderId) {
        return eventRepository.findAllBySectorLeaderId(leaderId);
    }
}
