package org.lightnsalt.hikingdom.domain.club.service;

public interface ClubMemberService {
	void sendClubJoinRequest(String email, Long clubId);

	void retractClubJoinRequest(String email, Long clubId);
}
