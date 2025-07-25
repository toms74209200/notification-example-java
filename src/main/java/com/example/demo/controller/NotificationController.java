package com.example.demo.controller;

import com.example.demo.api.NotificationsApi;
import com.example.demo.model.NotificationsIdGet200Response;
import com.example.demo.model.NotificationsSendPost200Response;
import com.example.demo.model.NotificationsSendPostRequest;
import com.example.demo.model.NotificationsSendPostRequestOneOf;
import com.example.demo.model.NotificationsSendPostRequestOneOf1;
import com.example.demo.model.NotificationsSendPostRequestOneOf2;
import com.example.demo.notifier.domain.EmailMessage;
import com.example.demo.notifier.domain.NotificationMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.notifier.domain.PushMessage;
import com.example.demo.notifier.domain.SmsMessage;
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

        NotificationMessage domainMessage = convertToDomain(notificationsSendPostRequest);
        NotificationResult domainResult = notificationService.sendNotification(domainMessage);
        NotificationsSendPost200Response response = convertFromDomain(domainResult);
        return ResponseEntity.ok(response);
    }

    private NotificationMessage convertToDomain(NotificationsSendPostRequest request) {
        if (request instanceof NotificationsSendPostRequestOneOf emailRequest) {
            return new EmailMessage(
                    emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        } else if (request instanceof NotificationsSendPostRequestOneOf1 smsRequest) {
            return new SmsMessage(smsRequest.getTo(), smsRequest.getMessage());
        } else if (request instanceof NotificationsSendPostRequestOneOf2 pushRequest) {
            return new PushMessage(
                    pushRequest.getDeviceToken(), pushRequest.getTitle(), pushRequest.getBody());
        } else {
            throw new IllegalArgumentException("Unsupported notification type");
        }
    }

    private NotificationsSendPost200Response convertFromDomain(NotificationResult result) {
        NotificationsSendPost200Response response = new NotificationsSendPost200Response();
        response.setId(result.id());
        response.setExternalMessageId(result.externalMessageId());
        response.setTimestamp(result.timestamp());

        switch (result.type()) {
            case EMAIL -> response.setType(NotificationsSendPost200Response.TypeEnum.EMAIL);
            case SMS -> response.setType(NotificationsSendPost200Response.TypeEnum.SMS);
            case PUSH -> response.setType(NotificationsSendPost200Response.TypeEnum.PUSH);
        }

        switch (result.status()) {
            case SENT -> response.setStatus(NotificationsSendPost200Response.StatusEnum.SENT);
            case FAILED -> response.setStatus(NotificationsSendPost200Response.StatusEnum.FAILED);
        }

        return response;
    }
}
