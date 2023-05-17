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
	private Long totalMember;
	private Long totalAssetCount;
	private Long totalHikingCount;
	private Long totalMountainCount;
	private String description;
	private List<ClubAssetRes> assets;

	public ClubDetailRes(boolean isJoin, Club club, List<ClubAsset> assets) {
		this.clubName = club.getName();
		this.isJoin = isJoin;
		this.totalMember = club.getTotalMemberCount();
		this.totalAssetCount = club.getTotalAssetCount();
		this.totalHikingCount = club.getTotalHikingCount();
		this.totalMountainCount = club.getTotalMountainCount();
		this.description = club.getDescription();
		this.assets = assets.stream().map(ClubAssetRes::new).collect(Collectors.toList());
	}
}
