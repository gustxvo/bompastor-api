package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;

public record UserProfileDto(String name, String email, UserRole role) {

    public static UserProfileDto fromEntity(User user) {
        return new UserProfileDto(user.name(), user.email(), user.role());
    }
}
