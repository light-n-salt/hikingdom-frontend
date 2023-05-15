package org.lightnsalt.hikingdom.service.notification.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateMeetupNotificationEvent {
    private List<ClubMember> clubMemberList;
    private Member host;
    private LocalDateTime startAt;

    public CreateMeetupNotificationEvent(List<ClubMember> clubMemberList, Member host, LocalDateTime startAt) {
        this.clubMemberList = clubMemberList;
        this.host = host;
        this.startAt = startAt;
    }
}
