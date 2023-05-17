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
	private Long totalMember;
	private Long totalAssetCount;
	private Long totalMeetupCount;
	private Long totalMountainCount;
	private Long score;
	private Long ranking;

	public ClubSearchRes(Club club, Long ranking) {
		this.clubId = club.getId();
		this.clubName = club.getName();
		this.totalMember = club.getTotalMemberCount();
		this.totalAssetCount = club.getTotalAssetCount();
		this.totalMeetupCount = club.getTotalMeetupCount();
		this.totalMountainCount = club.getTotalMountainCount();
		this.score = club.getScore();

		this.ranking = ranking;

		if (club.getBaseAddress() != null) {
			this.location = club.getBaseAddress().getSidoName() +
				(club.getBaseAddress().getGugunName() != null ? " " + club.getBaseAddress().getGugunName() : "");
		} else {
			this.location = "전국";
		}
	}
}
