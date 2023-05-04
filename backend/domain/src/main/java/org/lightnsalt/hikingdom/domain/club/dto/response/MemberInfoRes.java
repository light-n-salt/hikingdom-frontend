package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupMember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRes {
	private Long memberId;
	private String profileUrl;

	public MemberInfoRes(MeetupMember meetupMember) {
		this.memberId = meetupMember.getMember().getId();
		this.profileUrl = meetupMember.getMember().getProfileUrl();
	}
}
