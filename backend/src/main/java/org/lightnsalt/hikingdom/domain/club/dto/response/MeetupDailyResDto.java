package org.lightnsalt.hikingdom.domain.club.dto.response;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDailyResDto {

	private Long meetupId;
	private Long meetupHostId;
	private String meetupName;
	private String mountainName;
	private String description;
	private int totalMember;
	private LocalDateTime startAt;

	public MeetupDailyResDto(Meetup meetup) {
		this.meetupId = meetup.getId();
		this.meetupHostId = meetup.getHost().getId();
		this.meetupName = meetup.getName();
		this.mountainName = meetup.getMountain().getName();
		this.description = meetup.getDescription();
		this.startAt = meetup.getStartAt();
	}

}
