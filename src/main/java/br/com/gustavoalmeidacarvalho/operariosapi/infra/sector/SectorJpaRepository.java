package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SectorJpaRepository extends JpaRepository<SectorEntity, Integer> {

    Set<SectorEntity> findByLeaderId(UUID leaderId);

}
