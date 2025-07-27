package com.example.demo.notifier.domain;

public record SmsMessage(String to, String message) implements NotificationMessage {}
