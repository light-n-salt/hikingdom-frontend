package org.lightnsalt.hikingdom.service.club.dto.request;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupAddReq {
	private Long mountainId;
	private String name;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "올바른 날짜 형식이 아닙니다")
	private String startAt;
	private String description;
}
