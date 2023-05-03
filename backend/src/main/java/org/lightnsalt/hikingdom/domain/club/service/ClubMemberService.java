package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;
import java.util.Map;

import org.lightnsalt.hikingdom.domain.club.dto.response.MemberListRes;

public interface ClubMemberService {
	void sendClubJoinRequest(String email, Long clubId);

	void retractClubJoinRequest(String email, Long clubId);

	Map<String, List<MemberListRes>> findClubMember(String email, Long clubId);
}
