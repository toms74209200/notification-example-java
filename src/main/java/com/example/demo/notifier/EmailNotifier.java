package com.example.demo.notifier;

import com.example.demo.notifier.domain.EmailMessage;
import com.example.demo.notifier.domain.NotificationResult;
import com.example.demo.notifier.domain.NotificationStatus;
import com.example.demo.notifier.domain.NotificationType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier {

    private static final Logger log = LoggerFactory.getLogger(EmailNotifier.class);
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public NotificationResult notify(EmailMessage message, String notificationId) {
        log.info("Sending email notification to: {}", message.to());

        try {
            Map<String, Object> requestBody =
                    Map.of(
                            "to", message.to(),
                            "subject", message.subject(),
                            "body", message.body());

            String json = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(json, JSON);

            Request httpRequest =
                    new Request.Builder()
                            .url("http://localhost:8081/email/send")
                            .post(body)
                            .build();

            try (Response httpResponse = httpClient.newCall(httpRequest).execute()) {
                if (!httpResponse.isSuccessful()) {
                    throw new RuntimeException("HTTP request failed: " + httpResponse.code());
                }

                String responseBody = httpResponse.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                if (!jsonNode.has("messageId")) {
                    throw new RuntimeException("Response missing messageId field");
                }

                String messageId = jsonNode.get("messageId").asText();

                return new NotificationResult(
                        notificationId,
                        NotificationType.EMAIL,
                        NotificationStatus.SENT,
                        messageId,
                        LocalDateTime.now());
            }
        } catch (IOException e) {
            log.error("Failed to send email notification: {}", e.getMessage());
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}
