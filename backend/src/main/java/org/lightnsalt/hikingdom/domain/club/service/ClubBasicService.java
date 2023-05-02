package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.ClubAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubNameReq;

public interface ClubBasicService {
	Long addClub(String email, ClubAddReq clubAddReq);

	void checkDuplicateClubName(ClubNameReq clubNameReq);
}
