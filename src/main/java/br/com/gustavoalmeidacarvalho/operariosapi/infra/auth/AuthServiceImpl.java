package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.AuthService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.JwtTokenService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.InvalidUserCredentials;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.notification.UserNotificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final UserNotificationTokenRepository notificationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        User newUser = User.create(
                user.name(),
                user.email(),
                passwordEncoder.encode(user.password()),
                user.role()
        );
        return userService.save(newUser);
    }

    @Override
    public User loginWithEmailAndPassword(String email, String password) {
        return userService.findByEmail(email)
                .filter((user) -> passwordEncoder.matches(password, user.password()))
                .orElseThrow(InvalidUserCredentials::new);
    }

    @Transactional
    @Override
    public void logout(Long deviceId, UUID refreshToken) {
        if (deviceId != null) {
            notificationTokenRepository.deleteById(deviceId);
        }
        jwtTokenService.clearRefreshToken(refreshToken);
    }

}
