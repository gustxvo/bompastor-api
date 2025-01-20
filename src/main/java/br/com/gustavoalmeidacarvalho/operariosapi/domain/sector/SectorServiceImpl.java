package br.com.gustavoalmeidacarvalho.operariosapi.domain.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    @Override
    public Optional<Sector> findById(Integer sectorId) {
        return sectorRepository.findById(sectorId);
    }

    @Override
    public List<Sector> findByLeaderId(UUID leaderId) {
        return sectorRepository.findByLeaderId(leaderId);
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
        sector.workers().removeIf(user -> Objects.equals(user.getId(), worker.getId()));
        sectorRepository.save(sector);
    }

}
