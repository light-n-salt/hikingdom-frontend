package org.lightnsalt.hikingdom.service.club.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.ClubAsset;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClubDetailRes {
	private String clubName;
	@JsonProperty("isJoin")
	private boolean isJoin;
	private double participationRate;
	private Long totalDuration;
	private Long totalDistance;
	private Long totalAlt;
	private String description;
	private List<ClubAssetRes> assets;

	public ClubDetailRes(boolean isJoin, Club club, List<ClubAsset> assets) {
		this.clubName = club.getName();
		this.isJoin = isJoin;
		this.participationRate = club.getHikingStatistic().getParticipationRate();
		this.totalDuration = club.getHikingStatistic().getTotalDuration() / 60; // seconds to minutes
		this.totalDistance = club.getHikingStatistic().getTotalDistance();
		this.totalAlt = club.getHikingStatistic().getTotalAlt();
		this.description = club.getDescription();
		this.assets = assets.stream().map(ClubAssetRes::new).collect(Collectors.toList());
	}
}
