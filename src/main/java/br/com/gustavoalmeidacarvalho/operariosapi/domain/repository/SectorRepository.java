package br.com.gustavoalmeidacarvalho.operariosapi.domain.repository;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.sector.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {

    Set<Sector> findByLeaderId(UUID leaderId);
}
