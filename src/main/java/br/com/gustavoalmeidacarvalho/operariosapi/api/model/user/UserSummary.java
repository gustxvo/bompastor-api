package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.UUID;

public record UserSummary(UUID id, String name, String email) {

    public static UserSummary fromModel(User user) {
        return new UserSummary(user.id(), user.name(), user.email());
    }
}
