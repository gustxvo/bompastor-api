package br.com.gustavoalmeidacarvalho.operariosapi.domain.notification;

import java.util.UUID;

public record UserNotificationToken(Long deviceId, String token, UUID userId) {

    public static UserNotificationToken create(String token, UUID userId) {
        return new UserNotificationToken(null, token, userId);
    }

}
