package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.LogoutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserNotificationTokenRepository notificationTokenRepository;

    @Transactional
    @Override
    public void logout(Long deviceId, String refreshToken) {
        if (deviceId != null) {
            notificationTokenRepository.deleteById(deviceId);
        }
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
