package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.ClubInfoReq;

public interface ClubBasicService {
	Long addClub(String email, ClubInfoReq clubInfoReq);

	void modifyClub(String email, Long clubId, ClubInfoReq clubInfoReq);

	void checkDuplicateClubName(String clubName);
}
