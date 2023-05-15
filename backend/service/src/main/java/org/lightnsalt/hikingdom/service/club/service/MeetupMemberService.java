package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.MeetupMemberDetailListRes;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupMemberListRes;

public interface MeetupMemberService {
	void addJoinMeetup(String email, Long clubId, Long meetupId);

	void removeJoinMeetup(String email, Long clubId, Long meetupId);

	List<MeetupMemberDetailListRes> findMeetupMemberDetail(Long clubId, Long meetupId);

	MeetupMemberListRes findMeetupMember(String email, Long clubId, Long meetupId);
}
