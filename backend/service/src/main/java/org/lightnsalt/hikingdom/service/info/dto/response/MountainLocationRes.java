package org.lightnsalt.hikingdom.service.info.dto.response;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainLocationRes {
	private Long mountainId;
	private String name;
	private String address;
	private double maxAlt;
	private String imgUrl;
	private double mountainSummitLat;
	private double mountainSummitLng;

	public MountainLocationRes(MountainInfo mountainInfo) {
		this.mountainId = mountainInfo.getId();
		this.name = mountainInfo.getName();
		this.address = mountainInfo.getAddress();
		this.maxAlt = mountainInfo.getTopAlt();
		this.imgUrl = mountainInfo.getImgUrl();
		this.mountainSummitLat = mountainInfo.getTopLat();
		this.mountainSummitLng = mountainInfo.getTopLng();
	}
}
