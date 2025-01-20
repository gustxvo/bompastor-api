package br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SectorDto(@JsonProperty("sector_id") Integer id, @JsonProperty("sector_name") String name,
                        @JsonProperty("leader_id") UUID leaderId, @JsonProperty("workers") Set<UserSummary> workers) {

    public static SectorDto fromDomain(Sector sector) {
        UUID leaderId = sector.leader().getId();

        Set<UserSummary> workers = sector.workers().stream()
                .map((worker) -> new UserSummary(worker.getId(), worker.getName(), worker.getEmail()))
                .collect(Collectors.toSet());

        return new SectorDto(sector.id(), sector.name(), leaderId, workers);
    }
}
