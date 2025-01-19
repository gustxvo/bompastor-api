package br.com.gustavoalmeidacarvalho.operariosapi.infra.event;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(EventEntity::toModel)
                .toList();
    }

    @Override
    public Event create(Event event) {
        EventEntity eventEntity = new EventEntity(event);
        return jpaRepository.save(eventEntity).toModel();
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return jpaRepository.findById(eventId)
                .map(EventEntity::toModel);
    }

    @Override
    public boolean existsById(Long eventId) {
        return jpaRepository.existsById(eventId);
    }

    @Override
    public List<Event> findAllBySectorLeaderId(UUID leaderId) {
        return jpaRepository.findAllBySectorLeaderId(leaderId)
                .stream()
                .map(EventEntity::toModel)
                .toList();
    }

    @Override
    public List<Event> findAllByWorkerId(UUID workerId) {
        return jpaRepository.findAllByWorkers_Id(workerId)
                .stream()
                .map(EventEntity::toModel)
                .toList();
    }
}
