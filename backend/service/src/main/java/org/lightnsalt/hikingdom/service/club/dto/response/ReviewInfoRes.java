package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupReview;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("isOwner")
	private boolean isOwner;

	public ReviewInfoRes(MeetupReview meetupReview, boolean isOwner) {
		this.memberId = meetupReview.getMember().getId();
		this.reviewId = meetupReview.getId();
		this.nickname = meetupReview.getMember().getNickname();
		this.profileUrl = meetupReview.getMember().getProfileUrl();
		this.level = meetupReview.getMember().getLevel().getId();
		this.content = meetupReview.getContent();
		this.isOwner = isOwner;
	}
}
