package org.lightnsalt.hikingdom.service.notification.dto.event;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.domain.entity.info.AssetInfo;

import lombok.Getter;

@Getter
public class CreateClubAssetNotificationEvent {
	private final List<ClubMember> clubMemberList;
	private final AssetInfo assetInfo;

	public CreateClubAssetNotificationEvent(List<ClubMember> clubMemberList, AssetInfo assetInfo) {
		this.clubMemberList = clubMemberList;
		this.assetInfo = assetInfo;
	}
}
