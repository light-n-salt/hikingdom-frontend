package org.lightnsalt.hikingdom.service.club.dto.response.meetup;

import org.lightnsalt.hikingdom.domain.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupMemberDetailListRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private int level;
	private Long totalHikingCount;
	private Long totalDuration;
	private Long totalDistance;

	public MeetupMemberDetailListRes(Member member) {
		this.memberId = member.getId();
		this.nickname = member.getNickname();
		this.profileUrl = member.getProfileUrl();
		this.level = member.getLevel().getId();
		this.totalHikingCount = member.getHikingStatistic().getTotalHikingCount();
		this.totalDuration = member.getHikingStatistic().getTotalDuration();
		this.totalDistance = member.getHikingStatistic().getTotalDistance();
	}
}
