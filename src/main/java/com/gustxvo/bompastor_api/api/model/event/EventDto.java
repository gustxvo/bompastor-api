package com.gustxvo.bompastor_api.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustxvo.bompastor_api.api.model.sector.SectorDto;
import com.gustxvo.bompastor_api.domain.model.event.Event;

import java.time.LocalDateTime;

public record EventDto(Long id, @JsonProperty("date_time") LocalDateTime dateTime, SectorDto sector) {

    public static EventDto fromEntity(Event event) {
        return new EventDto(event.getId(), event.getDateTime(), SectorDto.fromEntity(event.getSector()));
    }
}
