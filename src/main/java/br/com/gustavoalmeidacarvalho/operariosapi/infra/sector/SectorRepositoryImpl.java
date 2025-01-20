package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SectorRepositoryImpl implements SectorRepository {

    private final SectorJpaRepository jpaRepository;

    @Override
    public List<Sector> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(SectorEntity::toModel)
                .toList();
    }

    @Override
    public Optional<Sector> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(SectorEntity::toModel);
    }

    @Override
    public List<Sector> findByLeaderId(UUID leaderId) {
        return jpaRepository.findByLeaderId(leaderId)
                .stream()
                .map(SectorEntity::toModel)
                .toList();
    }

    @Override
    public void save(Sector sector) {
        jpaRepository.save(new SectorEntity(sector));
    }

}
