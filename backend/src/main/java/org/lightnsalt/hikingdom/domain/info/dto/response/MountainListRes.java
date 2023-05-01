package org.lightnsalt.hikingdom.domain.info.dto.response;

import org.lightnsalt.hikingdom.domain.info.dto.repository.MountainInfoDtoInterface;
import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainListRes implements Comparable<MountainListRes> {
	private Long id;
	private String name;
	private String address;
	private double maxAlt;
	private String imgUrl;
	private int distance;

	public MountainListRes(MountainInfoDtoInterface mountain) {
		this.id = mountain.getMountainInfo().getId();
		this.name = mountain.getMountainInfo().getName();
		this.address = mountain.getMountainInfo().getAddress();
		this.maxAlt = mountain.getMountainInfo().getTopAlt();
		this.imgUrl = mountain.getMountainInfo().getImgUrl();
		this.distance = (int)Math.round(mountain.getDistance());
	}

	public MountainListRes(MountainInfo mountain) {
		this.id = mountain.getId();
		this.name = mountain.getName();
		this.address = mountain.getAddress();
		this.maxAlt = mountain.getTopAlt();
		this.imgUrl = mountain.getImgUrl();
	}

	@Override
	public int compareTo(MountainListRes o) {
		return this.distance - o.getDistance();
	}
}
