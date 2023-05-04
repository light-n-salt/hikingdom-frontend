package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupReview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoRes {
	private Long memberId;
	private Long reviewId;
	private String nickname;
	private String profileUrl;
	private String content;

	public ReviewInfoRes(MeetupReview meetupReview) {
		this.memberId = meetupReview.getMember().getId();
		this.reviewId = meetupReview.getId();
		this.nickname = meetupReview.getMember().getNickname();
		this.profileUrl = meetupReview.getMember().getProfileUrl();
		this.content = meetupReview.getContent();
	}
}
