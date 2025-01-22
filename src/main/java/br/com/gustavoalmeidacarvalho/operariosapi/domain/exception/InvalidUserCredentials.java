package br.com.gustavoalmeidacarvalho.operariosapi.domain.exception;

public class InvalidUserCredentials extends RuntimeException {

    public InvalidUserCredentials() {
        super("Email or password invalid");
    }
}
