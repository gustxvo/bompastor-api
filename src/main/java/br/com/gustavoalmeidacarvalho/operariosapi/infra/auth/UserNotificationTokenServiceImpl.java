package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.UserNotificationToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.UserNotificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationTokenServiceImpl implements UserNotificationTokenService {

    private final UserNotificationTokenRepository tokenRepository;

    @Override
    public boolean existsByToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    @Override
    public UserNotificationToken save(UserNotificationToken userToken) {
        UserNotificationTokenEntity tokenEntity = tokenRepository.save(new UserNotificationTokenEntity(userToken));
        return tokenEntity.toModel();
    }

    @Override
    public boolean existsById(Long deviceId) {
        return tokenRepository.existsById(deviceId);
    }

    @Override
    public void deleteById(Long deviceId) {
        tokenRepository.deleteById(deviceId);
    }

}
