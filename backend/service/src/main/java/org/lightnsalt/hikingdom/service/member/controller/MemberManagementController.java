package org.lightnsalt.hikingdom.service.member.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.service.member.service.MemberManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관리와 관련된 API
 * JWT 토큰 인증 필요
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberManagementController {
	private final MemberManagementService memberManagementService;

	@GetMapping
	public ResponseEntity<CustomResponseBody> memberInfoDetail(Authentication authentication) {
		MemberInfoRes memberInfoRes = memberManagementService.findMemberInfo(authentication.getName());
		return new ResponseEntity<>(BaseResponseBody.of("회원 정보 조회에 성공했습니다", memberInfoRes), HttpStatus.OK);
	}

	@DeleteMapping("/withdraw")
	public ResponseEntity<CustomResponseBody> memberRemove(Authentication authentication) {
		memberManagementService.removeMember(authentication.getName());
		return new ResponseEntity<>(BaseResponseBody.of("회원 탈퇴에 성공했습니다"), HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<CustomResponseBody> logout(@RequestHeader("Authorization") String bearerToken) {
		memberManagementService.logout(bearerToken);

		return new ResponseEntity<>(BaseResponseBody.of("로그아웃에 성공했습니다"), HttpStatus.OK);
	}

	@PutMapping("/password-change")
	public ResponseEntity<CustomResponseBody> passwordChange(Authentication authentication,
		@RequestBody @Valid MemberChangePasswordReq memberChangePasswordReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberManagementService.changePassword(authentication.getName(), memberChangePasswordReq);

		return new ResponseEntity<>(BaseResponseBody.of("비밀번호 변경에 성공했습니다"), HttpStatus.OK);
	}

	@PutMapping("/nickname-change")
	public ResponseEntity<CustomResponseBody> nicknameChange(Authentication authentication,
		@RequestBody @Valid MemberNicknameReq memberNicknameReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberManagementService.changeNickname(authentication.getName(), memberNicknameReq);

		return new ResponseEntity<>(BaseResponseBody.of("닉네임 변경에 성공했습니다"), HttpStatus.OK);
	}

	@PutMapping("/profile-image-change")
	public ResponseEntity<CustomResponseBody> profileImageChange(Authentication authentication, @RequestBody MultipartFile image) {
		String profileUrl = memberManagementService.changeProfileImage(authentication.getName(), image);

		return new ResponseEntity<>(BaseResponseBody.of("프로필 사진 변경에 성공했습니다", profileUrl), HttpStatus.OK);
	}
}
