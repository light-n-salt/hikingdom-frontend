package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupMemberListRes;

public interface MeetupMemberService {
	List<MeetupMemberListRes> findMeetupMember(Long clubId, Long meetupId);
}
