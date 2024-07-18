package com.gustxvo.bompastor_api.api.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceId(@JsonProperty("device_id") Long deviceId) {
}
