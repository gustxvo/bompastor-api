package br.com.gustavoalmeidacarvalho.operariosapi.domain.exception;

public class UserConflictException extends RuntimeException {

    public UserConflictException(String message) {
        super(message);
    }
}
