package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.service.member.dto.request.MemberLoginReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberRefreshTokenReq;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberTokenRes;

public interface MemberAuthService {

	MemberTokenRes login(MemberLoginReq memberLoginReq);

	MemberTokenRes refreshToken(MemberRefreshTokenReq memberRefreshTokenReq);
}
