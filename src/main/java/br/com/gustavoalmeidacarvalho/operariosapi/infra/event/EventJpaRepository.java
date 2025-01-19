package br.com.gustavoalmeidacarvalho.operariosapi.infra.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllBySectorLeaderId(UUID leaderId);

    List<EventEntity> findAllByWorkers_Id(UUID workerId);

}
