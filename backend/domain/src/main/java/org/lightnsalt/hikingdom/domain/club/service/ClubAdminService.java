package org.lightnsalt.hikingdom.domain.club.service;

public interface ClubAdminService {
	void acceptClubJoinRequest(String email, Long clubId, Long memberId);

	void rejectClubJoinRequest(String email, Long clubId, Long memberId);
}
