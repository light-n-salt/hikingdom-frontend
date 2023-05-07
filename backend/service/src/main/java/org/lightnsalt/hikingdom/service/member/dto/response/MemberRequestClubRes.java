package org.lightnsalt.hikingdom.service.member.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.Club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestClubRes {
	private Long clubId;
	private String clubName;
	private String location;
	private int totalMember;
	private int totalDuration;
	private int totalDistance;
	private double participationRate;
	private int ranking;

	public MemberRequestClubRes(Club club, int ranking) {
		this.clubId = club.getId();
		this.clubName = club.getName();
		this.location = club.getBaseAddress().getGugunName();
		this.totalMember = Math.toIntExact(club.getTotalMemberCount());
		if (club.getHikingStatistic() != null) {
			this.totalDuration = Math.round(club.getHikingStatistic().getTotalDuration());
			this.totalDistance = Math.round(club.getHikingStatistic().getTotalDistance());
			this.participationRate = club.getHikingStatistic().getParticipationRate();
		}
		this.ranking = ranking;
	}
}
