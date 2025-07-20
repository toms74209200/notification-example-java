package com.example.demo.notifier.domain;

public sealed interface NotificationMessage permits EmailMessage, SmsMessage, PushMessage {}