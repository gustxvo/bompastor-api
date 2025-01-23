package br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogoutRequest(@JsonProperty("device_id") Long deviceId,
                            @JsonProperty("refresh_token") String refreshToken) {
}
