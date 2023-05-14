package org.lightnsalt.hikingdom.service.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRes {
    @JsonProperty("notificationId")
    private Long id;
    private String title;
    private String body;
    private String sendAt;
    @JsonProperty("isRead")
    private boolean isRead;
}
