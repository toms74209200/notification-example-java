package com.example.demo.notifier.domain;

import java.time.LocalDateTime;

public record NotificationResult(
    String id,
    NotificationType type,
    NotificationStatus status,
    String externalMessageId,
    LocalDateTime timestamp
) {}