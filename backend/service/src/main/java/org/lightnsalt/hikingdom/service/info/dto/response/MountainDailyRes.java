package org.lightnsalt.hikingdom.service.info.dto.response;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MountainDailyRes {
	private Long mountainId;
	private String name;
	private double maxAlt;
	private String address;
	private String imgUrl;

	public MountainDailyRes(MountainInfo mountain) {
		this.mountainId = mountain.getId();
		this.name = mountain.getName();
		this.maxAlt = mountain.getTopAlt();
		this.address = mountain.getAddress();
		this.imgUrl = mountain.getImgUrl();
	}
}
