package org.lightnsalt.hikingdom.service.hiking.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class HikingShareLocationReq {
	@NotNull
	private Long clubId;

	@NotNull
	private Long meetupId;

	@NotNull
	private Long memberId;

	@NotEmpty
	private String nickname;

	@NotNull
	private Integer level;

	@NotEmpty
	private String profileUrl;
}
