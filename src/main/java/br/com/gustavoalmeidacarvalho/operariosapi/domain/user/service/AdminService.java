package br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    void changeSectorLeader(Integer sectorId, UUID leaderId);

    List<User> getAllUsers();

    User findUserById(UUID userId);

}
