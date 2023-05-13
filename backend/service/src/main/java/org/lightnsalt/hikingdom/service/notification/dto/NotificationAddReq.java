package org.lightnsalt.hikingdom.service.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lightnsalt.hikingdom.domain.entity.notification.Notification;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationAddReq {
    private String email;
    private String title;
    private String body;
    private LocalDateTime sendAt;
    private Boolean isRead;
}
