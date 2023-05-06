package org.lightnsalt.hikingdom.service.club.service;

import org.lightnsalt.hikingdom.service.club.dto.request.ClubInfoReq;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSimpleDetailRes;

public interface ClubBasicService {
	Long addClub(String email, ClubInfoReq clubInfoReq);

	void modifyClub(String email, Long clubId, ClubInfoReq clubInfoReq);

	ClubSimpleDetailRes findClubSimpleDetail(Long clubId);

	void checkDuplicateClubName(String clubName);
}
