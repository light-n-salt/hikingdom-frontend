package org.lightnsalt.hikingdom.service.club.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDetailRes {
	private Long meetupHostId;
	private Long meetupId;
	private String meetupName;
	private String mountainName;
	private String startAt;
	private String description;
	private int totalMember;
	private boolean isJoin;
	private List<MemberShortRes> memberInfo;
	private List<PhotoInfoRes> photoInfo;
	private List<ReviewInfoRes> reviewInfo;

	public MeetupDetailRes(Meetup meetup, int totalMember, boolean isJoin, List<MemberShortRes> memberInfos,
		List<PhotoInfoRes> photoInfos, List<ReviewInfoRes> reviewInfos) {
		this.meetupHostId = meetup.getHost().getId();
		this.meetupId = meetup.getId();
		this.meetupName = meetup.getName();
		this.mountainName = meetup.getMountain().getName();
		this.startAt = meetup.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.description = meetup.getDescription();
		this.totalMember = totalMember;
		this.isJoin = isJoin;
		this.memberInfo = memberInfos;
		this.photoInfo = photoInfos;
		this.reviewInfo = reviewInfos;
	}
}
