package com.example.demo.strategy;

import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;

public interface NotificationStrategy {
    boolean supports(NotificationMessage message);

    NotificationResult send(NotificationMessage message, String notificationId);
}
