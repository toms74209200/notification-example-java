package com.example.demo.strategy;

import com.example.demo.notifier.SmsNotifier;
import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.notifier.domain.SmsMessage;
import org.springframework.stereotype.Component;

@Component
public class SmsStrategy implements NotificationStrategy {

    private final SmsNotifier smsNotifier;

    public SmsStrategy(SmsNotifier smsNotifier) {
        this.smsNotifier = smsNotifier;
    }

    @Override
    public boolean supports(NotificationMessage message) {
        return message instanceof SmsMessage;
    }

    @Override
    public NotificationResult send(NotificationMessage message, String notificationId) {
        return smsNotifier.notify((SmsMessage) message, notificationId);
    }
}
