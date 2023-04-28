package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberSignUpReq;

public interface MemberSignUpService {
	void signUp(MemberSignUpReq memberSignUpReq);

	void checkDuplicateNickname(MemberNicknameReq memberNicknameReq);
}
