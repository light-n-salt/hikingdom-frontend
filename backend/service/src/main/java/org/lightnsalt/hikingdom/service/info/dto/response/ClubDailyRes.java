package org.lightnsalt.hikingdom.service.info.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.domain.entity.club.Club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubDailyRes {
	private Long clubId;
	private List<AssetInfoRes> assets;

	public ClubDailyRes(Club club) {
		this.clubId = club.getId();
		this.assets = club.getAssets().stream().map(AssetInfoRes::new).collect(Collectors.toList());
	}
}
