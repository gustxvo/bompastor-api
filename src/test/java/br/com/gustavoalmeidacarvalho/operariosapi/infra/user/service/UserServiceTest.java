package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserProfile;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User.USER;
import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole.ADMIN;
import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole.LEADER;
import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole.WORKER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenUsersExist_whenFindAllUsers_thenReturnAllUsers() {
        List<User> userList = List.of(
                new User(UUID.randomUUID(), "Test 1", "test1@email.com", "123", WORKER),
                new User(UUID.randomUUID(), "Test 2", "test2@email.com", "123", WORKER),
                new User(UUID.randomUUID(), "Test 3", "test3@email.com", "123", WORKER)
        );
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    void givenUserExists_whenFindUserById_thenReturnUser() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "Test", "test@email.com", "123", WORKER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User user = userService.findById(userId);

        assertThat(user).isNotNull();
        assertThat(user.id()).isEqualTo(userId);
    }

    @Test
    void givenUserNotExists_whenFindUserById_thenThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("%s %s not found", USER, userId);
    }

    @Test
    void givenUserEmailNotTaken_whenCreateUser_thenReturnCreatedUser() {
        String email = "test@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User user = User.create("Test", email, "123", WORKER);
        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    void givenUserEmailAlreadyTaken_whenCreateUser_thenThrowUserConflictException() {
        String email = "test@email.com";
        User existingUser = new User(UUID.randomUUID(), "T", email, "012", WORKER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        User newUser = User.create("Test", email, "123", WORKER);

        assertThatThrownBy(() -> userService.save(newUser))
                .isInstanceOf(UserConflictException.class)
                .hasMessage("Email already taken");
    }

    @Test
    void givenUserExists_whenUpdateUserWithSameEmail_thenReturnUpdatedUser() {
        String email = "test@email.com";
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Test", email, "123", WORKER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserProfile profile = new UserProfile(userId, "Test 1", email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        userService.updateProfile(profile);

        User updatedUser = new User(userId, profile.name(), profile.email(), user.password(), user.role());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void givenUserExists_whenUpdateUserWithEmailNotTaken_thenReturnUpdatedUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Test", "test@email.com", "123", WORKER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserProfile profile = new UserProfile(userId, "Test 1", "mytest@email.com");
        when(userRepository.findByEmail(profile.email())).thenReturn(Optional.empty());
        userService.updateProfile(profile);

        User updatedUser = new User(userId, profile.name(), profile.email(), user.password(), user.role());
        verify(userRepository).save(updatedUser);
    }

    @Test
    void givenUserExists_whenUpdateUserWithEmailAlreadyTaken_thenThrowUserConflictException() {
        UUID userId = UUID.randomUUID();
        User user = User.create("Test", "myemail@email.com", "123", WORKER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User otherUser = new User(UUID.randomUUID(), "T", "test@email.com", "111", WORKER);
        String emailAlreadyTaken = otherUser.email();
        when(userRepository.findByEmail(emailAlreadyTaken)).thenReturn(Optional.of(otherUser));

        UserProfile profile = new UserProfile(userId, "Test 1", emailAlreadyTaken);

        assertThatThrownBy(() -> userService.updateProfile(profile))
                .isInstanceOf(UserConflictException.class)
                .hasMessage("Email already taken");
    }

    @Test
    void givenUserNotExists_whenUpdateUser_thenThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserProfile userProfile = new UserProfile(userId, "Test", "test@email.com");
        assertThatThrownBy(() -> userService.updateProfile(userProfile))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("%s %s not found", USER, userId);
    }

    @Test
    void givenUserExists_whenDeleteUserById_thenDeleteUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void givenUserNotExists_whenDeleteUserById_thenThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("%s %s not found", USER, userId);
    }


    @Test
    void givenUsersExist_whenFindAllUsersById_thenReturnFoundUsers() {
        List<User> foundUsers = List.of(
                new User(UUID.randomUUID(), "Test 1", "test1@email.com", "123", WORKER),
                new User(UUID.randomUUID(), "Test 2", "test2@email.com", "123", WORKER),
                new User(UUID.randomUUID(), "Test 3", "test3@email.com", "123", LEADER),
                new User(UUID.randomUUID(), "Test 4", "test4@email.com", "123", ADMIN)
        );
        Set<UUID> userIds = foundUsers.stream().map(User::id).collect(Collectors.toSet());
        when(userRepository.findAllById(userIds)).thenReturn(foundUsers);

        List<User> users = userService.findAllById(userIds);

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(4);
    }

    @Test
    void givenAdminsLeadersAndWorkersExist_whenCurrentWorkersInSector_thenReturnWorkersAndLeaders() {
        User worker1 = new User(UUID.randomUUID(), "Test 1", "test1@email.com", "123", WORKER);
        User worker2 = new User(UUID.randomUUID(), "Test 2", "test2@email.com", "123", WORKER);
        User leader = new User(UUID.randomUUID(), "Test 3", "test3@email.com", "123", LEADER);
        User admin = new User(UUID.randomUUID(), "Test 4", "test4@email.com", "123", ADMIN);
        when(userRepository.findByRole(ADMIN)).thenReturn(List.of(admin));

        userService.getAvailableWorkers(Set.of(worker1));

        Set<UUID> excludedUsers = Set.of(worker1.id(), admin.id());
        verify(userRepository).findAllByIdExcept(excludedUsers);

        Set<UUID> workersAvailable = Set.of(worker2.id(), leader.id());
        verify(userRepository, times(0)).findAllByIdExcept(workersAvailable);
    }


}
