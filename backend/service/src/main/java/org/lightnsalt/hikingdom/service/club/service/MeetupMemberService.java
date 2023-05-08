package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.service.club.dto.response.MemberShortRes;

public interface MeetupMemberService {
	void addJoinMeetup(String email, Long clubId, Long meetupId);

	void removeJoinMeetup(String email, Long clubId, Long meetupId);

	List<MemberListRes> findMeetupMemberDetail(Long clubId, Long meetupId);

	List<MemberShortRes> findMeetupMember(Long clubId, Long meetupId);
}
