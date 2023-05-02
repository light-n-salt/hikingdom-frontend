package org.lightnsalt.hikingdom.domain.club.dto.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MeetupReviewRes {
	@NotNull
	private Long memberId;

	@NotEmpty
	private String nickname;

	private String profileUrl;

	@NotNull
	private Long reviewId;

	@NotEmpty
	private String content;
}
