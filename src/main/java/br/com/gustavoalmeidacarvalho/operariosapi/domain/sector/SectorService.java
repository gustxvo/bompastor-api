package br.com.gustavoalmeidacarvalho.operariosapi.domain.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectorService {

    Optional<Sector> findById(Integer sectorId);

    List<Sector> findByLeaderId(UUID leaderId);

    List<Sector> findAll();

    void changeLeader(Sector sector, User leader);

    void addWorker(Sector sector, User worker);

    void removeWorker(Sector sector, User worker);

}
