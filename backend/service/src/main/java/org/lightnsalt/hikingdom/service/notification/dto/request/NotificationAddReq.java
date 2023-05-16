package org.lightnsalt.hikingdom.service.notification.dto.request;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.domain.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationAddReq {
    private Member member;
    private String title;
    private String body;
    private String url;
    private LocalDateTime sendAt;
}
