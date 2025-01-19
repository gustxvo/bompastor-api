package br.com.gustavoalmeidacarvalho.operariosapi.domain.event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    List<Event> findAll();

    Event create(Event event);

    Optional<Event> findById(Long eventId);

    boolean existsById(Long eventId);

    List<Event> findAllBySectorLeaderId(UUID leaderId);

    List<Event> findAllByWorkerId(UUID workerId);
}
