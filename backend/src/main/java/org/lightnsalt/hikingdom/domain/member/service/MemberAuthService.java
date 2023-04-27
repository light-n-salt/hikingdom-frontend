package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberLoginReq;
import org.lightnsalt.hikingdom.domain.member.dto.response.MemberTokenRes;

public interface MemberAuthService {

	MemberTokenRes login(MemberLoginReq memberLoginReq);

	MemberTokenRes refreshToken(String bearerRefreshToken);
}
