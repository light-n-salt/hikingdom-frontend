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
public class HikingRecordListRes {
	private Long hikingRecordId;
	private String mountainName;
	private LocalDateTime startAt;
	private int totalDuration;
	private double totalDistance;
	private double maxAlt;
	@JsonProperty("isMeetup")
	private boolean isMeetup;
	private Long meetupId;
	private String meetupName;

	public HikingRecordListRes(MemberHiking memberHiking) {
		this.hikingRecordId = memberHiking.getId();
		this.mountainName = memberHiking.getMountain().getName();
		this.startAt = memberHiking.getStartAt();
		this.totalDuration = Math.round(memberHiking.getTotalDuration());
		this.totalDistance = memberHiking.getTotalDistance();
		this.maxAlt = memberHiking.getTotalAlt();
		this.isMeetup = memberHiking.getIsMeetup();
		if (this.isMeetup) {
			this.meetupId = memberHiking.getMeetup().getId();
			this.meetupName = memberHiking.getMeetup().getName();
		}
	}
}
