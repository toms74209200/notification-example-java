package com.example.demo.strategy;

import com.example.demo.notifier.PushNotifier;
import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.notifier.domain.PushMessage;
import org.springframework.stereotype.Component;

@Component
public class PushStrategy implements NotificationStrategy {

    private final PushNotifier pushNotifier;

    public PushStrategy(PushNotifier pushNotifier) {
        this.pushNotifier = pushNotifier;
    }

    @Override
    public boolean supports(NotificationMessage message) {
        return message instanceof PushMessage;
    }

    @Override
    public NotificationResult send(NotificationMessage message, String notificationId) {
        return pushNotifier.notify((PushMessage) message, notificationId);
    }
}
