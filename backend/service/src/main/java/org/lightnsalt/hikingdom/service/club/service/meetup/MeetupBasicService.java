package org.lightnsalt.hikingdom.service.club.service.meetup;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.request.MeetupAddReq;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupDailyRes;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupDetailRes;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupMonthlyRes;

public interface MeetupBasicService {
	Long addMeetup(String email, Long clubId, MeetupAddReq req);

	void removeMeetup(String email, Long clubId, Long meetupId);

	MeetupMonthlyRes findMeetupMonthly(Long clubId, String month);

	List<MeetupDailyRes> findMeetupDaily(Long clubId, String date);

	MeetupDetailRes findMeetup(String email, Long clubId, Long meetupId);
}
