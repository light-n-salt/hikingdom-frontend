package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRes {
	private Long memberId;
	private String nickname;
	private String profileUrl;
	private Integer level;
}
