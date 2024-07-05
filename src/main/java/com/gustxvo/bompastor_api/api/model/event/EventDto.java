package com.gustxvo.bompastor_api.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustxvo.bompastor_api.domain.model.event.Event;

import java.time.LocalDateTime;

public record EventDto(Long id, @JsonProperty("date_time") LocalDateTime dateTime) {

    public static EventDto fromEntity(Event event) {
        return new EventDto(event.getId(), event.getDateTime());
    }
}
