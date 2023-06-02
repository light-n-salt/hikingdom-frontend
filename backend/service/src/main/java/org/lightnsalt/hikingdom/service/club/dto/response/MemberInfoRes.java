package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private Integer level;

	public MemberInfoRes(MeetupMember meetupMember) {
		this.memberId = meetupMember.getMember().getId();
		this.nickname = meetupMember.getMember().getNickname();
		this.profileUrl = meetupMember.getMember().getProfileUrl();
		this.level = meetupMember.getMember().getLevel().getId();
	}

	public MemberInfoRes(Member member) {
		this.memberId = member.getId();
		this.nickname = member.getNickname();
		this.profileUrl = member.getProfileUrl();
		this.level = member.getLevel().getId();
	}
}
