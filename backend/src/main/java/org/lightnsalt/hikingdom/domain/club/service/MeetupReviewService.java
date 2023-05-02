package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupReviewReq;

public interface MeetupReviewService {
	Long saveMeetupReview(String email, Long clubId, Long meetupId, MeetupReviewReq meetupReviewReq);
}
