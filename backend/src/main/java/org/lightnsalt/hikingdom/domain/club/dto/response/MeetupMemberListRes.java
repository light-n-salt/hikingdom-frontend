package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupMember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupMemberListRes {
	private Long memberId;
	private String profileUrl;

	public MeetupMemberListRes(MeetupMember meetupMember) {
		this.memberId = meetupMember.getMember().getId();
		this.profileUrl = meetupMember.getMember().getProfileUrl();
	}
}
