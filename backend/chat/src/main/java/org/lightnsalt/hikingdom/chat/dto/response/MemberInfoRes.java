package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.Data;

@Data
public class MemberInfoRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private Long level;
}
