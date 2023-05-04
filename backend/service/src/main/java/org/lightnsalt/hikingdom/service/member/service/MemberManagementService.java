package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.service.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes;
import org.springframework.web.multipart.MultipartFile;

public interface MemberManagementService {
	MemberInfoRes findMemberInfo(String email);

	void logout(String bearerToken);

	void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq);

	void changeNickname(String email, MemberNicknameReq memberNicknameReq);

	String changeProfileImage(String email, MultipartFile photo);
}
