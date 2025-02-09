package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector.SECTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectorServiceImplTest {

    @Mock
    private SectorRepository sectorRepository;

    @Autowired
    @InjectMocks
    private SectorServiceImpl sectorService;

    @Test
    void givenSectorExist_whenFindAllSector_thenReturnAllSector() {
        List<Sector> sectors = List.of(
                new Sector(1, "Estudos", null, Set.of()),
                new Sector(2, "Ceia", null, Set.of()),
                new Sector(3, "Mídia", null, Set.of())
        );
        when(sectorRepository.findAll()).thenReturn(sectors);

        List<Sector> sector = sectorService.findAll();

        assertThat(sector).isNotNull();
        assertThat(sector).hasSize(3);
    }

    @Test
    void givenSectorExists_whenFindSectorById_thenReturnSector() {
        int sectorId = 1;
        Sector existingSector = new Sector(sectorId, "Estudos", null, Set.of());
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.of(existingSector));

        Sector sector = sectorService.findById(sectorId);

        assertThat(sector).isNotNull();
        assertThat(sector.id()).isEqualTo(sectorId);
    }

    @Test
    void givenSectorNotExists_whenFindSectorById_thenThrowSectorNotFoundException() {
        int sectorId = 1;
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sectorService.findById(sectorId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("%s %s not found", SECTOR, sectorId);
    }


    @Test
    void givenSectorExists_whenChangeLeader_thenReturnUpdatedSector() {
        int sectorId = 1;
        User newLeader = new User(UUID.randomUUID(), "Test", "test@email.com", "123", UserRole.WORKER);
        Sector existingSector = new Sector(sectorId, "Estudos", null, Set.of());
        Sector updatedSector = new Sector(sectorId, "Estudos", newLeader, Set.of());

        sectorService.changeLeader(existingSector, newLeader);

        verify(sectorRepository).save(updatedSector);
    }


}
