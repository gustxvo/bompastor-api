package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;

import java.util.UUID;

public record UserDto(UUID id, String name, String email, UserRole role) {

    public static UserDto fromDomain(User user) {
        return new UserDto(user.id(), user.name(), user.email(), user.role());
    }
}
