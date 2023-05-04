package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupReview;

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
	private Integer level;
	private String content;

	public ReviewInfoRes(MeetupReview meetupReview) {
		this.memberId = meetupReview.getMember().getId();
		this.reviewId = meetupReview.getId();
		this.nickname = meetupReview.getMember().getNickname();
		this.profileUrl = meetupReview.getMember().getProfileUrl();
		this.level = meetupReview.getMember().getLevel().getId();
		this.content = meetupReview.getContent();
	}
}
