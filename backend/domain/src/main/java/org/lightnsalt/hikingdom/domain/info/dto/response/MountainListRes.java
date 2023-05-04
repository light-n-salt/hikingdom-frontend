package org.lightnsalt.hikingdom.domain.info.dto.response;

import org.lightnsalt.hikingdom.domain.info.dto.repository.MountainInfo;
import org.lightnsalt.hikingdom.entity.info.MountainDailyInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainListRes {
	private Long id;
	private String name;
	private String address;
	private double maxAlt;
	private String imgUrl;

	public MountainListRes(MountainInfo mountain) {
		this.id = mountain.getId();
		this.name = mountain.getName();
		this.address = mountain.getAddress();
		this.maxAlt = mountain.getMaxAlt();
		this.imgUrl = mountain.getImgUrl();
	}

	public MountainListRes(org.lightnsalt.hikingdom.entity.info.MountainInfo mountain) {
		this.id = mountain.getId();
		this.name = mountain.getName();
		this.address = mountain.getAddress();
		this.maxAlt = mountain.getTopAlt();
		this.imgUrl = mountain.getImgUrl();
	}

	public MountainListRes(MountainDailyInfo daily) {
		this.id = daily.getMountain().getId();
		this.name = daily.getMountain().getName();
		this.address = daily.getMountain().getAddress();
		this.maxAlt = daily.getMountain().getTopAlt();
		this.imgUrl = daily.getMountain().getImgUrl();
	}

}
