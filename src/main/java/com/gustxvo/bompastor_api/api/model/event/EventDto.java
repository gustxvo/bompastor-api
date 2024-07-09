package com.gustxvo.bompastor_api.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustxvo.bompastor_api.api.model.sector.SectorSummary;
import com.gustxvo.bompastor_api.domain.model.event.Event;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record EventDto(Long id, @JsonProperty("date_time") LocalDateTime dateTime, SectorSummary sector,
                       Set<WorkerSummary> workers) {

    public static EventDto fromEntity(Event event) {
        SectorSummary sector = SectorSummary.fromEntity(event.getSector());
        Set<WorkerSummary> workers = event.getWorkers().stream()
                .map(WorkerSummary::fromEntity)
                .collect(Collectors.toSet());

        return new EventDto(event.getId(), event.getDateTime(), sector, workers);
    }
}
