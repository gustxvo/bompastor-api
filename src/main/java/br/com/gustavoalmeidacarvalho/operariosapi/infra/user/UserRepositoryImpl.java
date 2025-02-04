package br.com.gustavoalmeidacarvalho.operariosapi.infra.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = jpaRepository.save(new UserEntity(user));
        return userEntity.toModel();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return jpaRepository.findById(userId)
                .map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserEntity::toModel);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public List<User> findWorkers() {
        return jpaRepository.findByRoleNot(UserRole.ADMIN).stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public List<User> findAllById(Iterable<UUID> userIds) {
        return jpaRepository.findAllById(userIds).stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public Set<User> findAllByIdExcept(Iterable<UUID> userIds) {
        return jpaRepository.findAllByIdNotIn(userIds).stream()
                .map(UserEntity::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteById(UUID userId) {
        jpaRepository.deleteById(userId);
    }

    @Override
    public boolean existsById(UUID userId) {
        return jpaRepository.existsById(userId);
    }

}
