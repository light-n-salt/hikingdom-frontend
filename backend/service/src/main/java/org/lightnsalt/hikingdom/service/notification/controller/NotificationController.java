package org.lightnsalt.hikingdom.service.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.hiking.dto.response.TodayMeetupRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.service.notification.dto.NotificationRes;
import org.lightnsalt.hikingdom.service.notification.service.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/members/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping
    public ResponseEntity<CustomResponseBody> notificationList(Authentication authentication,  @RequestParam(defaultValue = "") Long id, Pageable pageable) {
        CustomSlice<NotificationRes> result = notificationService.findNotificationList(authentication.getName(), id, pageable);
        return new ResponseEntity<>(BaseResponseBody.of("알림 목록 조회에 성공했습니다", result), HttpStatus.OK);
    }
}
