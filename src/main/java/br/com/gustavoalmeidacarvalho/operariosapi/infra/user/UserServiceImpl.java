package br.com.gustavoalmeidacarvalho.operariosapi.infra.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return userRepository.findById(uuid).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new UserConflictException("Email already taken");
        }
        UserEntity newUser = userRepository.save(new UserEntity(user));
        return newUser.toModel();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::toModel);
    }

    @Override
    public List<User> findByRole(UserRole userRole) {
        return userRepository.findByRole(userRole)
                .stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Set<User> findAllById(Set<UUID> workerIds) {
        return userRepository.findAllById(workerIds)
                .stream()
                .map(UserEntity::toModel)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> getAvailableWorkers(Set<UUID> workerIds) {
        return userRepository.findAllByIdNotIn(workerIds)
                .stream().map(UserEntity::toModel)
                .collect(Collectors.toSet());
    }
}
