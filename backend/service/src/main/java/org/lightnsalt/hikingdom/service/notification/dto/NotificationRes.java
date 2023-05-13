package org.lightnsalt.hikingdom.service.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRes {
    private Long id;
    private String title;
    private String body;
    private LocalDateTime sendAt;
    private boolean isRead;
}
