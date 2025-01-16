package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;

import java.util.UUID;

public record UserSummary(UUID id, String name, String email) {

    public static UserSummary fromEntity(User user) {
        return new UserSummary(user.getId(), user.getName(), user.getEmail());
    }
}
