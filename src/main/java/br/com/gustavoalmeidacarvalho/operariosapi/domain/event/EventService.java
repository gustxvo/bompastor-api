package br.com.gustavoalmeidacarvalho.operariosapi.domain.event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event create(Event event);

    List<Event> findAllBySectorLeaderId(UUID leaderId);

    List<Event> findAllByWorkerId(UUID workerId);

    boolean existsById(Long eventId);

    Optional<Event> findById(Long eventId);
}
