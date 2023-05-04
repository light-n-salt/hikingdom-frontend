package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.service.member.dto.request.MemberSignUpReq;

public interface MemberSignUpService {
	void signUp(MemberSignUpReq memberSignUpReq);

	void checkDuplicateNickname(String nickname);
}
