package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

public interface AuthService {

    User register(User user);

}
