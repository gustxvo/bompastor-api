package br.com.gustavoalmeidacarvalho.operariosapi.domain.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.List;

public interface SectorService {

    Sector findById(Integer sectorId);

    List<Sector> findAll();

    void changeLeader(Sector sector, User leader);

    void addWorker(Sector sector, User worker);

    void removeWorker(Sector sector, User worker);

}
