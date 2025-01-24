package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector.SECTOR;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;
    private final UserService userService;

    @Override
    public Sector findById(Integer sectorId) {
        return sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException(SECTOR, sectorId));
    }

    @Override
    public List<Sector> findAll() {
        return sectorRepository.findAll();
    }

    @Override
    public void changeLeader(Sector sector, User leader) {
        var updatedSector = new Sector(sector.id(), sector.name(), leader, sector.workers());
        sectorRepository.save(updatedSector);
    }

    @Override
    public void addWorker(Sector sector, User worker) {
        sector.workers().add(worker);
        sectorRepository.save(sector);
    }

    @Override
    public void removeWorker(Sector sector, User worker) {
        sector.workers().removeIf(user -> Objects.equals(user.id(), worker.id()));
        sectorRepository.save(sector);
    }

}
