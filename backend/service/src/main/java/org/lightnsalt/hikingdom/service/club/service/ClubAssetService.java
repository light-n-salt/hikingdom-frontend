package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.ClubAssetRes;

public interface ClubAssetService {
	List<ClubAssetRes> findClubMountainList(Long clubId);
}
