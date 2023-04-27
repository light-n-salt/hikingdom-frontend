package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberSignUpReq;

public interface MemberAuthService {
	Long signUp(MemberSignUpReq memberSignUpReq);
}
