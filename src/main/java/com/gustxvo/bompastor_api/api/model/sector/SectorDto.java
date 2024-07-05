package com.gustxvo.bompastor_api.api.model.sector;

import com.gustxvo.bompastor_api.api.model.user.UserSummary;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;

import java.util.Set;
import java.util.stream.Collectors;

public record SectorDto(Integer id, String name, UserSummary leader, Set<UserSummary> workers) {

    public static SectorDto fromEntity(Sector sector) {
        UserSummary leader = new UserSummary(sector.getLeader().getId(),
                sector.getLeader().getName(), sector.getLeader().getEmail());
        Set<UserSummary> workers = sector.getUsers().stream()
                .map((worker) -> new UserSummary(worker.getId(), worker.getName(),
                        worker.getEmail())).collect(Collectors.toSet());

        return new SectorDto(sector.getId(), sector.getName(), leader, workers);
    }
}
