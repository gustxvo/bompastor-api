package br.com.gustavoalmeidacarvalho.operariosapi.domain.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.UUID;

public interface JwtTokenService {

    JwtTokenPair generateJwtTokenPair(User user);

    JwtTokenPair invalidateRefreshToken(UUID refreshToken);

    void clearRefreshToken(UUID refreshToken);

}
