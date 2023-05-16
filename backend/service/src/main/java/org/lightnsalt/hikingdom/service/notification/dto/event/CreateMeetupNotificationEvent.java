package org.lightnsalt.hikingdom.service.notification.dto.event;

import java.time.LocalDateTime;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import lombok.Getter;

@Getter
public class CreateMeetupNotificationEvent {
	private final List<ClubMember> clubMemberList;
	private final Member host;
	private final Long meetupId;
	private final LocalDateTime startAt;

	public CreateMeetupNotificationEvent(List<ClubMember> clubMemberList, Member host, Long meetupId,
		LocalDateTime startAt) {
		this.clubMemberList = clubMemberList;
		this.host = host;
		this.meetupId = meetupId;
		this.startAt = startAt;
	}
}
