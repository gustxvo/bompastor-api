package br.com.gustavoalmeidacarvalho.operariosapi.infra.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserRole userRole);

    List<UserEntity> findByRoleNot(UserRole role);

    List<UserEntity> findAllByIdNotIn(Iterable<UUID> workerIds);

}
