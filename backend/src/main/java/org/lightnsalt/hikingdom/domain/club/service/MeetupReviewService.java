package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupReviewReq;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupReviewRes;

public interface MeetupReviewService {
	Long saveMeetupReview(String email, Long clubId, Long meetupId, MeetupReviewReq meetupReviewReq);

	void removeMeetupReview(String email, Long clubId, Long meetupId, Long reviewId);

	List<MeetupReviewRes> findMeetupReviewList(String email, Long clubId, Long meetupId);
}
