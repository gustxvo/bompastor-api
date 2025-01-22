package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.UUID;

public interface AuthService {

    User register(User user);

    User loginWithEmailAndPassword(String email, String password);

    void logout(Long deviceId, UUID refreshToken);

}
