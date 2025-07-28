package com.example.demo.service;

import com.example.demo.notifier.EmailNotifier;
import com.example.demo.notifier.PushNotifier;
import com.example.demo.notifier.SmsNotifier;
import com.example.demo.notifier.domain.EmailMessage;
import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.notifier.domain.PushMessage;
import com.example.demo.notifier.domain.SmsMessage;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("conditional")
public class ConditionalNotificationService implements NotificationService {

    private final EmailNotifier emailNotifier;
    private final SmsNotifier smsNotifier;
    private final PushNotifier pushNotifier;

    public ConditionalNotificationService(
            EmailNotifier emailNotifier, SmsNotifier smsNotifier, PushNotifier pushNotifier) {
        this.emailNotifier = emailNotifier;
        this.smsNotifier = smsNotifier;
        this.pushNotifier = pushNotifier;
    }

    @Override
    public NotificationResult sendNotification(NotificationMessage message) {
        String notificationId = UUID.randomUUID().toString();

        if (message instanceof EmailMessage emailMessage) {
            return emailNotifier.notify(emailMessage, notificationId);
        } else if (message instanceof SmsMessage smsMessage) {
            return smsNotifier.notify(smsMessage, notificationId);
        } else if (message instanceof PushMessage pushMessage) {
            return pushNotifier.notify(pushMessage, notificationId);
        } else {
            throw new IllegalArgumentException(
                    "Unsupported notification message type: " + message.getClass());
        }
    }
}
