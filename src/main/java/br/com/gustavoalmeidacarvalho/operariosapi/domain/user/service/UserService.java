package br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    User findById(UUID uuid);

    User save(User user);

    User updateProfile(UserProfile userProfile);

    Optional<User> findByEmail(String email);

    Set<User> findAllById(Set<UUID> workers);

    Set<User> getAvailableWorkers(Set<User> workersInSector);

    void deleteById(UUID userId);
}
