package com.gustxvo.bompastor_api.api.model.sector;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record WorkerIdInput(@JsonProperty("worker_id") String workerId) {

    public UUID uuid() {
        return UUID.fromString(workerId);
    }
}
