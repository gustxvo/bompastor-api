package com.gustxvo.bompastor_api.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustxvo.bompastor_api.domain.model.event.Event;

import java.time.LocalDateTime;

public record EventSummary(@JsonProperty("event_id") Long eventId, @JsonProperty("sector_name") String sectorName,
                           @JsonProperty("date_time") LocalDateTime dateTime) {

    public static EventSummary fromEntity(Event event) {
        return new EventSummary(event.getId(), event.getSector().getName(), event.getDateTime());
    }
}
