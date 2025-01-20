package br.com.gustavoalmeidacarvalho.operariosapi.domain.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;

import java.util.Set;

public record Sector(Integer id, String name, User leader, Set<User> workers) {
}
