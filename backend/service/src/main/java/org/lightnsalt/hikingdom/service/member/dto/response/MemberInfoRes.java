package org.lightnsalt.hikingdom.service.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private Integer level;
}

