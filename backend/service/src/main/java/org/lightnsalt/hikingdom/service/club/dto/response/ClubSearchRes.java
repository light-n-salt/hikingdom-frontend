package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.Club;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubSearchRes {
	private Long clubId;
	private String clubName;
	private String location;
	private int totalMember;
	private Long totalDuration; // in minutes
	private Long totalDistance; // in meters
	private double participationRate;
	private Long ranking;

	public ClubSearchRes(Club club, Long ranking) {
		this.clubId = club.getId();
		this.clubName = club.getName();
		this.totalDuration = club.getHikingStatistic().getTotalDuration() / 60; // seconds to minutes
		this.totalDistance = club.getHikingStatistic().getTotalDistance();
		this.participationRate = club.getHikingStatistic().getParticipationRate();
		this.ranking = ranking;

		if (club.getBaseAddress() != null) {
			this.location = club.getBaseAddress().getSidoName() +
				(club.getBaseAddress().getGugunName() != null ? " " + club.getBaseAddress().getGugunName() : "");
		} else {
			this.location = "전국";
		}
	}
}
