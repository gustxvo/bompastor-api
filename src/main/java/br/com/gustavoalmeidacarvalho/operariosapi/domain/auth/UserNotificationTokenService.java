package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

public interface UserNotificationTokenService {

    boolean existsByToken(String token);

    UserNotificationToken save(UserNotificationToken userToken);

    boolean existsById(Long deviceId);

    void deleteById(Long deviceId);

}
