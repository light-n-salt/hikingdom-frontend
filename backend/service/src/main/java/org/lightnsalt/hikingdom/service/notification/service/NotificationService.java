package org.lightnsalt.hikingdom.service.notification.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.service.notification.dto.NotificationRes;
import org.lightnsalt.hikingdom.service.notification.dto.NotificationAddReq;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    Long addNotification(NotificationAddReq notificationAddReq);

    CustomSlice<NotificationRes> findNotificationList(String email, Long notificationId, Pageable pageable);
}
