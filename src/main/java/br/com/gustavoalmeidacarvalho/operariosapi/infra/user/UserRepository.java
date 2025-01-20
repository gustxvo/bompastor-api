package br.com.gustavoalmeidacarvalho.operariosapi.infra.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByIdNotIn(Set<UUID> workerIds);

    List<UserEntity> findByRole(UserRole role);

    List<UserEntity> findByRoleNot(UserRole role);

}
