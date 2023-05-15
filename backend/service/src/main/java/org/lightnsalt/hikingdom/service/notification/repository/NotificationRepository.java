package org.lightnsalt.hikingdom.service.notification.repository;

import org.lightnsalt.hikingdom.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findAllByMemberId(Long memberId);
}
