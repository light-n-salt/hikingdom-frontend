package org.lightnsalt.hikingdom.service.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationReq {
    private Long targetUserId;
    private String title;
    private String body;
    // private String image;
    // private Map<String, String> data;

    @Builder
    public FCMNotificationReq(Long targetUserId, String title, String body) {
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;
    }
}
