package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

public interface LogoutService {

    void logout(Long deviceId, String refreshToken);

}
