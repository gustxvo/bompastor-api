package br.com.gustavoalmeidacarvalho.operariosapi.domain.exception;

import java.util.UUID;

public class ExpiredRefreshTokenException extends RuntimeException {

    public ExpiredRefreshTokenException(UUID refreshToken) {
        super(refreshToken + " refresh token expired. Please log in again");
    }
}
