package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupDailyRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupMonthlyRes;

public interface MeetupBasicService {
	Long saveMeetup(String email, Long clubId, MeetupAddReq req);

	MeetupMonthlyRes findMeetupMonthly(Long clubId, String month);

	List<MeetupDailyRes> findMeetupDaily(Long clubId, String date);
}
