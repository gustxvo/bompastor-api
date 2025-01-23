package br.com.gustavoalmeidacarvalho.operariosapi.domain.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String type;
    private final String identifier;

    public ResourceNotFoundException(String type, Object identifier) {
        super();
        this.type = type;
        this.identifier = identifier.toString();
    }

    @Override
    public String getMessage() {
        return String.format("%s %s not found", type, identifier);
    }
}
