package br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceId(@JsonProperty("device_id") Long deviceId) {
}
