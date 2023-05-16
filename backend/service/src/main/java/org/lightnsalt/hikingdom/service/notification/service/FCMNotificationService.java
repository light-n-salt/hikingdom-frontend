package org.lightnsalt.hikingdom.service.notification.service;

import org.lightnsalt.hikingdom.service.notification.dto.FCMNotificationReq;

public interface FCMNotificationService {
    void sendNotificationByToken(FCMNotificationReq requestDto);
}
