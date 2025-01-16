package br.com.gustavoalmeidacarvalho.operariosapi.domain.repository;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllBySectorLeaderId(UUID leaderId);

    List<Event> findAllByWorkers_Id(UUID workerId);
}
