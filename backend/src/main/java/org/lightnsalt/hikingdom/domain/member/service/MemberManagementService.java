package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.dto.response.MemberInfoRes;

public interface MemberManagementService {
	MemberInfoRes findMemberInfo(String email);

	void logout(String bearerToken);

	void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq);

	void changeNickname(String email, MemberNicknameReq memberNicknameReq);
}
