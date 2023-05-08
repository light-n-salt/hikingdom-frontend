package org.lightnsalt.hikingdom.service.club.dto.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubSimpleDetailRes {
	@NotNull
	private Long hostId;
	@NotNull
	private Long clubId;
	@NotEmpty
	private String clubName;
}
