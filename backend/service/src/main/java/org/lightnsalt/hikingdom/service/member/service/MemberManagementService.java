package org.lightnsalt.hikingdom.service.member.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberProfileRes;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberRequestClubRes;
import org.springframework.web.multipart.MultipartFile;

public interface MemberManagementService {
	MemberInfoRes findMemberInfo(String email);

	void removeMember(String email);

	void logout(String bearerToken);

	void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq);

	void changeNickname(String email, MemberNicknameReq memberNicknameReq);

	String changeProfileImage(String email, MultipartFile photo);

	MemberProfileRes findProfile(String nickname);

	List<MemberRequestClubRes> findRequestClub(String email);
}
