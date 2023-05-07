package org.lightnsalt.hikingdom.service.member.dto.response;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikingRecordRes {
	private Long hikingRecordId;
	private String mountainName;
	private LocalDateTime startAt;
	private Long totalDuration;
	private double totalDistance;
	private double maxAlt;

	@JsonProperty("isMeetup")
	private boolean isMeetup;
	private Long meetupId;
	private String meetupName;

	public HikingRecordRes(MemberHiking hiking) {
		this.hikingRecordId = hiking.getId();
		this.mountainName = hiking.getMountain().getName();
		this.startAt = hiking.getStartAt();
		this.totalDuration = hiking.getTotalDuration();
		this.totalDistance = hiking.getTotalDistance();
		this.maxAlt = hiking.getTotalAlt();
		this.isMeetup = hiking.getIsMeetup();
		if (this.isMeetup) {
			this.meetupId = hiking.getMeetup().getId();
			this.meetupName = hiking.getMeetup().getName();
		}

	}
}