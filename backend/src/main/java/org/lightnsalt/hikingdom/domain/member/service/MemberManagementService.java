package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;

public interface MemberManagementService {
	void logout(String bearerToken);

	void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq);

	void changeNickname(String email, MemberNicknameReq memberNicknameReq);
}
