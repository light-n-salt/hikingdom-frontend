package org.lightnsalt.hikingdom.domain.info.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainGetRes {
	private Long id;
	private String name;
	private String description;
	private String address;
	private String imgUrl;
	private double topAlt;
	private String peaks;
	private List<AssetMountainRes> assets;
	private int totalDuration;

	public MountainGetRes(final MountainInfo mountainInfo) {
		this.id = mountainInfo.getId();
		this.name = mountainInfo.getName();
		this.description = mountainInfo.getDescription();
		this.address = mountainInfo.getAddress();
		this.imgUrl = mountainInfo.getImgUrl();
		this.topAlt = mountainInfo.getTopAlt();
		this.peaks = mountainInfo.getPeaks();
		this.assets = mountainInfo.getAsset().stream().map(AssetMountainRes::new).collect(Collectors.toList());
		this.totalDuration = mountainInfo.getTotalDuration();
	}
}
