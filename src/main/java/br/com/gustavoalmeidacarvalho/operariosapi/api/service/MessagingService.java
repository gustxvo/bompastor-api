package br.com.gustavoalmeidacarvalho.operariosapi.api.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification.NotificationMessage;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserNotificationToken;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserNotificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserNotificationTokenRepository tokenRepository;

    public void sendNotification(Set<UUID> users, NotificationMessage notificationMessage) {
        Set<String> userTokens = userNotificationTokens(users);
        if (userTokens.isEmpty()) {
            return;
        }

        Notification notification = Notification.builder()
//                .setImage("https://imgur.com/vkdveuG.jpeg")
//                .setTitle(notificationMessage.title())
//                .setBody(notificationMessage.body())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(userTokens)
                .putAllData(notificationMessage.data())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.sendEachForMulticast(message);
        } catch (FirebaseMessagingException messagingException) {
            throw new IllegalStateException(messagingException.getMessage());
        }
    }

    private Set<String> userNotificationTokens(Set<UUID> users) {
        return tokenRepository.findAllByUser_IdIn(users).stream()
                .map(UserNotificationToken::getToken)
                .collect(Collectors.toSet());
    }

}
