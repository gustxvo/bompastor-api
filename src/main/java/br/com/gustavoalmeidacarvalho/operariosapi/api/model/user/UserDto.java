package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserRole;

import java.util.UUID;

public record UserDto(UUID id, String name, String email, UserRole role) {

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
