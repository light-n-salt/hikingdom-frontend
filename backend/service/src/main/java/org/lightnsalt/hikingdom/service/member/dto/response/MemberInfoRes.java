package org.lightnsalt.hikingdom.service.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoRes {
	private String email;
	private String nickname;
	private String profileUrl;
	private Integer level;
	private Long clubId;
}
