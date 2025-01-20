package br.com.gustavoalmeidacarvalho.operariosapi.api.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.List;

public record WorkerNames(List<String> workers) {

    public static WorkerNames fromUserList(List<User> users) {
        return new WorkerNames(users.stream().map(User::name).toList());
    }
}
