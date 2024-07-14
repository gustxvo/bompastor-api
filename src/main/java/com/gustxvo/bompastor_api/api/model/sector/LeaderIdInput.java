package com.gustxvo.bompastor_api.api.model.sector;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LeaderIdInput(@JsonProperty("leader_id") String leaderId) {

    public UUID uuid() {
        return UUID.fromString(leaderId);
    }
}