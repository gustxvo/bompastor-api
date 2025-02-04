package br.com.gustavoalmeidacarvalho.operariosapi.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> findWorkers();

    List<User> findAllById(Iterable<UUID> userIds);

    Set<User> findAllByIdExcept(Iterable<UUID> userIds);

    void deleteById(UUID userId);

    boolean existsById(UUID userId);

}
