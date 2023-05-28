package org.lightnsalt.hikingdom.service.notification.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.notification.dto.response.NotificationRes;
import org.lightnsalt.hikingdom.service.notification.dto.request.NotificationAddReq;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Long addNotification(NotificationAddReq notificationAddReq);

    CustomSlice<NotificationRes> findNotificationList(String email, Long notificationId, Pageable pageable);
}
