package br.com.gustavoalmeidacarvalho.operariosapi.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(UUID uuid);

    User save(User user);

    boolean existsById(UUID userId);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole userRole);

    Set<User> findAllById(Set<UUID> workers);

    Set<User> getAvailableWorkers(Set<UUID> workerIds);

    void deleteById(UUID userId);

}
