package org.lightnsalt.hikingdom.service.member.dto.request;

import lombok.Data;

@Data
public class MemberRefreshTokenReq {
	private String refreshToken; // includes "Bearer " prefix
}
