package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserProfile;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.UserService;
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
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER, userId));
    }

    @Override
    public User save(User user) {
        if (emailAlreadyTakenByAnotherUser(user)) {
            throw new UserConflictException("Email already taken");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(UserProfile userProfile) {
        User user = findById(userProfile.userId());
        User updatedUser = new User(user.id(), userProfile.name(), userProfile.email(), user.password(), user.role());
        return save(updatedUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(USER, userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAllById(Set<UUID> workerIds) {
        return userRepository.findAllById(workerIds);
    }

    @Override
    public Set<User> getAvailableWorkers(Set<User> workersInSector) {
        Set<UUID> excludedWorkers = workersInSector.stream()
                .filter(user -> !user.isAdmin())
                .map(User::id)
                .collect(Collectors.toSet());

        return userRepository.findAllByIdExcept(excludedWorkers);
    }

    private boolean emailAlreadyTakenByAnotherUser(User user) {
        return userRepository.findByEmail(user.email())
                .filter((foundUser -> !Objects.equals(foundUser.id(), user.id())))
                .isPresent();
    }
}
