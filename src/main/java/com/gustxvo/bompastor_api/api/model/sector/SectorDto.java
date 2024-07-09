package com.gustxvo.bompastor_api.api.model.sector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustxvo.bompastor_api.api.model.user.UserSummary;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SectorDto(@JsonProperty("sector_id") Integer id, @JsonProperty("sector_name") String name,
                        @JsonProperty("leader_id") UUID leaderId, @JsonProperty("workers") Set<UserSummary> workers) {

    public static SectorDto fromEntity(Sector sector) {
        UUID leaderId = sector.getLeader().getId();

        Set<UserSummary> workers = sector.getWorkers().stream()
                .map((worker) -> new UserSummary(worker.getId(), worker.getName(), worker.getEmail()))
                .collect(Collectors.toSet());

        return new SectorDto(sector.getId(), sector.getName(), leaderId, workers);
    }
}
