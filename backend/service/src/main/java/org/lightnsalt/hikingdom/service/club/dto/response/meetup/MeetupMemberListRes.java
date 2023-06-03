package org.lightnsalt.hikingdom.service.club.dto.response.meetup;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.MemberShortRes;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupMemberListRes {
	private int totalMember;
	@JsonProperty("isJoin")
	private boolean isJoin;
	private List<MemberShortRes> memberInfo;

}
