package br.com.gustavoalmeidacarvalho.operariosapi.api.model.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserRole;

public record RegisterRequest(String name, String email, String password, UserRole role) {
}
