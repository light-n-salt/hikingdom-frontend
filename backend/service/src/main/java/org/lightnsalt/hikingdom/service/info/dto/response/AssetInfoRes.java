package org.lightnsalt.hikingdom.service.info.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.ClubAsset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetInfoRes {
	private String assetUrl;
	private int row;
	private int column;

	public AssetInfoRes(ClubAsset clubAsset) {
		this.assetUrl = clubAsset.getAsset().getAssetUrl();
		this.row = clubAsset.getRowIndex();
		this.column = clubAsset.getColIndex();
	}
}
