package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;
import java.util.Map;

import org.lightnsalt.hikingdom.service.club.dto.response.MeetupMemberDetailListRes;

public interface ClubMemberService {
	void sendClubJoinRequest(String email, Long clubId);

	void retractClubJoinRequest(String email, Long clubId);

	Map<String, List<MeetupMemberDetailListRes>> findClubMember(String email, Long clubId);

	void withdrawClubMember(String email, Long clubId);
}
