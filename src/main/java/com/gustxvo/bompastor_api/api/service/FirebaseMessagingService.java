package com.gustxvo.bompastor_api.api.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.gustxvo.bompastor_api.api.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationByToken(NotificationMessage notificationMessage) {
        Notification notification = Notification.builder()
                .setTitle(notificationMessage.title())
                .setBody(notificationMessage.body())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(notificationMessage.tokens())
                .setNotification(notification)
                .build();

        try {

            firebaseMessaging.sendEachForMulticast(message);
        } catch (FirebaseMessagingException messagingException) {
            throw new IllegalStateException(messagingException.getMessage());
        }
    }

}
