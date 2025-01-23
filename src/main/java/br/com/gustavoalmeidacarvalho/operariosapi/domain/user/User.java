package br.com.gustavoalmeidacarvalho.operariosapi.domain.user;

import java.util.UUID;

public record User (UUID id, String name, String email, String password, UserRole role) {

    public static final String USER = "user";

    public static User create(String name, String email, String password, UserRole role) {
        return new User(null, name, email, password, role);
    }
}
