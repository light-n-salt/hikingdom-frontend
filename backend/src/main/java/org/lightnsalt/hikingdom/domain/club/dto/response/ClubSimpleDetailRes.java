package org.lightnsalt.hikingdom.domain.club.dto.response;

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
	private Long groupId;

	@NotEmpty
	private String groupName;
}
