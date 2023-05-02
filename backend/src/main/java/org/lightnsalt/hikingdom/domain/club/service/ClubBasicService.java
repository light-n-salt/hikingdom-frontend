package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.ClubAddReq;

public interface ClubBasicService {
	Long addClub(String email, ClubAddReq clubAddReq);
}
