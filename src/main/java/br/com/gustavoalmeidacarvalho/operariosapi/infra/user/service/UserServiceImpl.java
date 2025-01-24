package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserProfile;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.UserService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toModel)
                .orElseThrow(() -> new ResourceNotFoundException(USER, userId));
    }

    @Override
    public User save(User user) {
        if (emailAlreadyTaken(user)) {
            throw new UserConflictException("Email already taken");
        }
        UserEntity newUser = userRepository.save(new UserEntity(user));
        return newUser.toModel();
    }

    @Override
    public User updateProfile(UserProfile userProfile) {
        User user = findById(userProfile.userId());
        User updatedUser = new User(user.id(), userProfile.name(), userProfile.email(), user.password(), user.role());
        return save(updatedUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::toModel);
    }

    @Override
    public void deleteById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(USER, userId);
        }
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
    public Set<User> getAvailableWorkers(Set<User> workersInSector) {
        Set<UUID> excludedWorkers = workersInSector.stream()
                .filter(user -> !user.isAdmin())
                .map(User::id)
                .collect(Collectors.toSet());

        return userRepository.findAllByIdNotIn(excludedWorkers)
                .stream()
                .map(UserEntity::toModel)
                .collect(Collectors.toSet());
    }

    private boolean emailAlreadyTaken(User user) {
        return userRepository.findByEmail(user.email())
                .filter((userEntity -> !Objects.equals(userEntity.getId(), user.id())))
                .isPresent();
    }
}
