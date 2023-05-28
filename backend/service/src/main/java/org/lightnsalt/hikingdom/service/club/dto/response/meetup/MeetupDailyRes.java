package org.lightnsalt.hikingdom.service.club.dto.response.meetup;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDailyRes {
	private Long meetupId;
	private Long meetupHostId;
	private String meetupName;
	private String mountainName;
	private String description;
	private int totalMember;
	private String startAt;

	public MeetupDailyRes(Meetup meetup) {
		this.meetupId = meetup.getId();
		this.meetupHostId = meetup.getHost().getId();
		this.meetupName = meetup.getName();
		this.mountainName = meetup.getMountain().getName();
		this.description = meetup.getDescription();
		this.startAt = meetup.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
