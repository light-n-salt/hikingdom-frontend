package org.lightnsalt.hikingdom.service.club.dto.response.meetup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupReview;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetupReviewRes {
	@NotNull
	private Long memberId;
	@NotEmpty
	private String nickname;
	private String profileUrl;
	@NotNull
	private Integer level;
	@NotNull
	private Long reviewId;
	@NotEmpty
	private String content;

	public MeetupReviewRes(MeetupReview meetupReview) {
		this.memberId = meetupReview.getMember().getId();
		this.nickname = meetupReview.getMember().getNickname();
		this.profileUrl = meetupReview.getMember().getProfileUrl();
		this.level = meetupReview.getMember().getLevel().getId();
		this.reviewId = meetupReview.getId();
		this.content = meetupReview.getContent();
	}
}
