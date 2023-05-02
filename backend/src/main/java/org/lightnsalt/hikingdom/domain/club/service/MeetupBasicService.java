package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupAddReq;

public interface MeetupBasicService {
	Long saveMeetup(String email, Long clubId, MeetupAddReq req);
}
