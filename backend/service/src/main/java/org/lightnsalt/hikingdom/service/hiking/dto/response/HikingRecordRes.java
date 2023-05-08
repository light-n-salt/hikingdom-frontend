package org.lightnsalt.hikingdom.service.hiking.dto.response;

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
	private int totalDuration;
	private int totalDistance;
	private double maxAlt;

	@JsonProperty("isMeetup")
	private boolean isMeetup;
	private Long meetupId;
	private String meetupName;

	public HikingRecordRes(MemberHiking hiking) {
		this.hikingRecordId = hiking.getId();
		this.mountainName = hiking.getMountain().getName();
		this.startAt = hiking.getStartAt();
		this.totalDuration = Math.round(hiking.getTotalDuration());
		this.totalDistance = Math.round(hiking.getTotalDistance());
		this.maxAlt = hiking.getTotalAlt();
		this.isMeetup = hiking.getIsMeetup();
		if (this.isMeetup) {
			this.meetupId = hiking.getMeetup().getId();
			this.meetupName = hiking.getMeetup().getName();
		}

	}
}