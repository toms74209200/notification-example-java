package com.example.demo.notifier.domain;

public record PushMessage(String deviceToken, String title, String body)
        implements NotificationMessage {}
