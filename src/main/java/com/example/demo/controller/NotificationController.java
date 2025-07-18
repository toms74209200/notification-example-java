package com.example.demo.controller;

import com.example.demo.api.NotificationsApi;
import com.example.demo.model.NotificationsIdGet200Response;
import com.example.demo.model.NotificationsSendPost200Response;
import com.example.demo.model.NotificationsSendPostRequest;
import com.example.demo.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController implements NotificationsApi {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<NotificationsIdGet200Response> notificationsIdGet(String id) {
        log.info("Getting notification status for id: {}", id);
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public ResponseEntity<NotificationsSendPost200Response> notificationsSendPost(
            NotificationsSendPostRequest notificationsSendPostRequest) {
        log.info("Sending notification: {}", notificationsSendPostRequest);

        try {
            NotificationsSendPost200Response response =
                    notificationService.sendNotification(notificationsSendPostRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid notification type: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
