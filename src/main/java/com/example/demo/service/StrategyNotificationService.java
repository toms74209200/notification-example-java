package com.example.demo.service;

import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.strategy.NotificationStrategy;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("strategy")
public class StrategyNotificationService implements NotificationService {

    private final List<NotificationStrategy> strategies;

    public StrategyNotificationService(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public NotificationResult sendNotification(NotificationMessage message) {
        String notificationId = UUID.randomUUID().toString();

        NotificationStrategy strategy =
                strategies.stream()
                        .filter(s -> s.supports(message))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "No strategy found for message type: "
                                                        + message.getClass()));

        return strategy.send(message, notificationId);
    }
}
