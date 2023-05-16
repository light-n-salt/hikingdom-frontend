package org.lightnsalt.hikingdom.service.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.entity.notification.Notification;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.lightnsalt.hikingdom.service.notification.dto.request.FCMNotificationReq;
import org.lightnsalt.hikingdom.service.notification.dto.request.NotificationAddReq;
import org.lightnsalt.hikingdom.service.notification.dto.response.NotificationRes;
import org.lightnsalt.hikingdom.service.notification.repository.NotificationRepository;
import org.lightnsalt.hikingdom.service.notification.repository.NotificationRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryCustom notificationRepositoryCustom;
    private final MemberRepository memberRepository;
    private final FCMNotificationService fcmNotificationService;

    @Transactional
    @Override
    public Long addNotification(NotificationAddReq notificationAddReq) {
        Notification notification = Notification.builder()
                .member(notificationAddReq.getMember())
                .title(notificationAddReq.getTitle())
                .body(notificationAddReq.getBody())
                .sendAt(notificationAddReq.getSendAt())
                .isRead(false)
                .build();
        final Notification savedNotification = notificationRepository.save(notification);

        fcmNotificationService.sendNotificationByToken(
                FCMNotificationReq.builder()
                        .targetUserId(notificationAddReq.getMember().getId())
                        .title(notificationAddReq.getTitle())
                        .body(notificationAddReq.getBody())
                        .build());

        return savedNotification.getId();
    }

    @Transactional
    @Override
    public CustomSlice<NotificationRes> findNotificationList(String email, Long notificationId, Pageable pageable) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

        Slice<Notification> notifications = notificationRepositoryCustom.findNotificationsByMemberId(member.getId(), notificationId, pageable);

        return new CustomSlice<>(
                notifications.map(notification -> new NotificationRes(
                        notification.getId(),
                        notification.getTitle(),
                        notification.getBody(),
                        notification.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        notification.isRead()
        )));
    }
}
