package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.ClubAsset;

import lombok.Data;

@Data
public class ClubAssetRes {
	private String mountainName;
	private Long meetupId;
	private String assetUrl;
	private int row;
	private int column;

	public ClubAssetRes(ClubAsset clubAsset) {
		this.mountainName = clubAsset.getMeetup().getMountain().getName();
		this.meetupId = clubAsset.getMeetup().getId();
		this.assetUrl = clubAsset.getAsset().getAssetUrl();
		this.row = clubAsset.getRowIndex();
		this.column = clubAsset.getColIndex();
	}
}
