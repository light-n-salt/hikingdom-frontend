package org.lightnsalt.hikingdom.service.club.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupAddReq {
	private Long mountainId;
	private String name;
	private String startAt;
	private String description;
}
