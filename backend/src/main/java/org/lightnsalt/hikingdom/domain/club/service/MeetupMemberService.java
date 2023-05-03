package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.dto.response.MemberListRes;

public interface MeetupMemberService {
	List<MemberListRes> findMeetupMember(Long clubId, Long meetupId);
}
