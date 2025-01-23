package br.com.gustavoalmeidacarvalho.operariosapi.domain.user;

import java.util.UUID;

public record UserProfile(UUID userId, String name, String email) {
}
