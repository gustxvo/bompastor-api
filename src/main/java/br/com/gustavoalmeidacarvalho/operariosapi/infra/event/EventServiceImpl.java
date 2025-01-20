package br.com.gustavoalmeidacarvalho.operariosapi.infra.event;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Optional<Event> findById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public Event create(Event event) {
        return eventRepository.create(event);
    }

    @Override
    public boolean existsById(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    @Override
    public List<Event> findAllBySectorLeaderId(UUID leaderId) {
        return eventRepository.findAllBySectorLeaderId(leaderId);
    }

    @Override
    public List<Event> findAllByWorkerId(UUID workerId) {
        return eventRepository.findAllByWorkerId(workerId);
    }
}
