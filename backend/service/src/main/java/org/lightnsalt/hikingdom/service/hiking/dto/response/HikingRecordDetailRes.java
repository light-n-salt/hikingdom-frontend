package org.lightnsalt.hikingdom.service.hiking.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikingRecordDetailRes {
	private Map<String, Object> gpsRoute;
	private String mountainName;
	private String startAt;
	private int totalDistance;
	private double maxAlt;
	private int totalDuration;

	public HikingRecordDetailRes(MemberHiking memberHiking) {
		this.gpsRoute = memberHiking.getGpsRoute().getGpsRoute();
		this.mountainName = memberHiking.getMountain().getName();
		this.startAt = memberHiking.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.totalDistance = Math.round(memberHiking.getTotalDistance());
		this.maxAlt = memberHiking.getTotalAlt();
		this.totalDuration = Math.round(memberHiking.getTotalDuration());
	}
}
