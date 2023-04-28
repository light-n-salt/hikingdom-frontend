package org.lightnsalt.hikingdom.domain.member.dto.request;

import lombok.Data;

@Data
public class MemberRefreshTokenReq {
	private String refreshToken; // includes "Bearer " prefix
}
