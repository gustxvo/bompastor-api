package com.gustxvo.bompastor_api.api.service;

import com.gustxvo.bompastor_api.domain.repository.RefreshTokenRepository;
import com.gustxvo.bompastor_api.domain.repository.UserNotificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserNotificationTokenRepository notificationTokenRepository;

    @Transactional
    public void logout(Long deviceId, String refreshToken) {
        if (deviceId != null) {
            notificationTokenRepository.deleteById(deviceId);
        }
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
