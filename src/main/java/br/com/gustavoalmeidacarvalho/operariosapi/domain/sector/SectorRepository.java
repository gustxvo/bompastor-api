package br.com.gustavoalmeidacarvalho.operariosapi.domain.sector;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectorRepository {

    List<Sector> findAll();

    Optional<Sector> findById(Integer id);

    List<Sector> findByLeaderId(UUID leaderId);

    void save(Sector sector);

}
