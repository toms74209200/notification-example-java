package com.example.demo.notifier.domain;

public record EmailMessage(String to, String subject, String body) implements NotificationMessage {}
