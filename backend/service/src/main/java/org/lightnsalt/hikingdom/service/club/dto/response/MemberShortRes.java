package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberShortRes {
	private Long memberId;
	private String profileUrl;

	public MemberShortRes(MeetupMember meetupMember) {
		this.memberId = meetupMember.getMember().getId();
		this.profileUrl = meetupMember.getMember().getProfileUrl();
	}
}
