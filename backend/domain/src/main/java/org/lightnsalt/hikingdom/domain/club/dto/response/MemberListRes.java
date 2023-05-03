package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberListRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private int level;
	private Long totalHikingCount;
	private Long totalDuration;
	private Long totalDistance;

	public MemberListRes(Member member) {
		this.memberId = member.getId();
		this.nickname = member.getNickname();
		this.profileUrl = member.getProfileUrl();
		this.level = member.getLevel().getId();
		this.totalHikingCount = member.getHikingStatistic().getTotalHikingCount();
		this.totalDuration = member.getHikingStatistic().getTotalDuration();
		this.totalDistance = member.getHikingStatistic().getTotalDistance();
	}
}
