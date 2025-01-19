package br.com.gustavoalmeidacarvalho.operariosapi.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.SectorSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;

import java.time.LocalDateTime;

public record EventSummary(@JsonProperty("event_id") Long eventId, SectorSummary sector,
                           @JsonProperty("date_time") LocalDateTime dateTime) {

    public static EventSummary fromDomain(Event event) {
        return new EventSummary(event.id(),
                SectorSummary.fromEntity(event.sector()),event.dateTime());
    }
}
