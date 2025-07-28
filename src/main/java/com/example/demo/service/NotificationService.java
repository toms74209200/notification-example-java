package com.example.demo.service;

import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;

public interface NotificationService {
    NotificationResult sendNotification(NotificationMessage message);
}
