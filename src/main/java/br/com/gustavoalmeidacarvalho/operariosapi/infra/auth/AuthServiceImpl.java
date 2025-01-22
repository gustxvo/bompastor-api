package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.AuthService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        User newUser = User.create(
                user.name(),
                user.email(),
                passwordEncoder.encode(user.password()),
                user.role());
        return userService.save(newUser);
    }
}
