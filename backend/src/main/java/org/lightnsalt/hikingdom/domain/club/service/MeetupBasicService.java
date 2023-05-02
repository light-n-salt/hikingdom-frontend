package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupMonthlyResDto;

public interface MeetupBasicService {
	Long saveMeetup(String email, Long clubId, MeetupAddReq req);

	MeetupMonthlyResDto findMeetupMonthly(Long clubId, String month);
}
