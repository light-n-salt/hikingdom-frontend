package org.lightnsalt.hikingdom.domain.info.dto.response;

import org.lightnsalt.hikingdom.entity.info.AssetInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetMountainRes {
	private Long assetId;
	private String name;
	private String assetUrl;
	private String getCondition;

	public AssetMountainRes(final AssetInfo asset) {
		this.assetId = asset.getId();
		this.name = asset.getName();
		this.assetUrl = asset.getAssetUrl();
		this.getCondition = asset.getGetCondition();
	}
}

