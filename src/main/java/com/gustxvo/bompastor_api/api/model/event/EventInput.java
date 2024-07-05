package com.gustxvo.bompastor_api.api.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventInput(@JsonProperty("sector_id") Integer sectorId,
                         @JsonProperty("date_time") LocalDateTime dateTime, Set<UUID> workers) {
}
