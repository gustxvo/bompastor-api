package br.com.gustavoalmeidacarvalho.operariosapi.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.SectorSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record EventDto(Long id, @JsonProperty("date_time") LocalDateTime dateTime, SectorSummary sector,
                       Set<WorkerSummary> workers) {

    public static EventDto fromDomain(Event event) {
        SectorSummary sector = SectorSummary.fromEntity(event.sector());
        Set<WorkerSummary> workers = event.workers().stream()
                .map(WorkerSummary::fromEntity)
                .collect(Collectors.toSet());

        return new EventDto(event.id(), event.dateTime(), sector, workers);
    }
}
