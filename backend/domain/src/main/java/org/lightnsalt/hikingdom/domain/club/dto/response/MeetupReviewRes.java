package org.lightnsalt.hikingdom.domain.club.dto.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
}
