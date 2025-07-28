package com.example.demo.strategy;

import com.example.demo.notifier.EmailNotifier;
import com.example.demo.notifier.domain.EmailMessage;
import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import org.springframework.stereotype.Component;

@Component
public class EmailStrategy implements NotificationStrategy {

    private final EmailNotifier emailNotifier;

    public EmailStrategy(EmailNotifier emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    @Override
    public boolean supports(NotificationMessage message) {
        return message instanceof EmailMessage;
    }

    @Override
    public NotificationResult send(NotificationMessage message, String notificationId) {
        return emailNotifier.notify((EmailMessage) message, notificationId);
    }
}
